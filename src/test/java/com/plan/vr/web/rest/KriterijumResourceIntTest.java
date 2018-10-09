package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.Kriterijum;
import com.plan.vr.domain.AkcioniPlan;
import com.plan.vr.repository.KriterijumRepository;
import com.plan.vr.service.KriterijumService;
import com.plan.vr.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.plan.vr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.plan.vr.domain.enumeration.KriterijumTip;
/**
 * Test class for the KriterijumResource REST controller.
 *
 * @see KriterijumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class KriterijumResourceIntTest {

    private static final KriterijumTip DEFAULT_KRITERIJUM_TIP = KriterijumTip.BOD;
    private static final KriterijumTip UPDATED_KRITERIJUM_TIP = KriterijumTip.VREDNOST;

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final Float DEFAULT_PONDER = 1F;
    private static final Float UPDATED_PONDER = 2F;

    @Autowired
    private KriterijumRepository kriterijumRepository;

    

    @Autowired
    private KriterijumService kriterijumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKriterijumMockMvc;

    private Kriterijum kriterijum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KriterijumResource kriterijumResource = new KriterijumResource(kriterijumService);
        this.restKriterijumMockMvc = MockMvcBuilders.standaloneSetup(kriterijumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kriterijum createEntity(EntityManager em) {
        Kriterijum kriterijum = new Kriterijum()
            .kriterijumTip(DEFAULT_KRITERIJUM_TIP)
            .naziv(DEFAULT_NAZIV)
            .ponder(DEFAULT_PONDER);
        // Add required entity
        AkcioniPlan akcioniPlan = AkcioniPlanResourceIntTest.createEntity(em);
        em.persist(akcioniPlan);
        em.flush();
        kriterijum.setAkcioniPlan(akcioniPlan);
        return kriterijum;
    }

    @Before
    public void initTest() {
        kriterijum = createEntity(em);
    }

    @Test
    @Transactional
    public void createKriterijum() throws Exception {
        int databaseSizeBeforeCreate = kriterijumRepository.findAll().size();

        // Create the Kriterijum
        restKriterijumMockMvc.perform(post("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isCreated());

        // Validate the Kriterijum in the database
        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeCreate + 1);
        Kriterijum testKriterijum = kriterijumList.get(kriterijumList.size() - 1);
        assertThat(testKriterijum.getKriterijumTip()).isEqualTo(DEFAULT_KRITERIJUM_TIP);
        assertThat(testKriterijum.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testKriterijum.getPonder()).isEqualTo(DEFAULT_PONDER);
    }

    @Test
    @Transactional
    public void createKriterijumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kriterijumRepository.findAll().size();

        // Create the Kriterijum with an existing ID
        kriterijum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKriterijumMockMvc.perform(post("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isBadRequest());

        // Validate the Kriterijum in the database
        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKriterijumTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = kriterijumRepository.findAll().size();
        // set the field null
        kriterijum.setKriterijumTip(null);

        // Create the Kriterijum, which fails.

        restKriterijumMockMvc.perform(post("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isBadRequest());

        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = kriterijumRepository.findAll().size();
        // set the field null
        kriterijum.setNaziv(null);

        // Create the Kriterijum, which fails.

        restKriterijumMockMvc.perform(post("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isBadRequest());

        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPonderIsRequired() throws Exception {
        int databaseSizeBeforeTest = kriterijumRepository.findAll().size();
        // set the field null
        kriterijum.setPonder(null);

        // Create the Kriterijum, which fails.

        restKriterijumMockMvc.perform(post("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isBadRequest());

        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKriterijums() throws Exception {
        // Initialize the database
        kriterijumRepository.saveAndFlush(kriterijum);

        // Get all the kriterijumList
        restKriterijumMockMvc.perform(get("/api/kriterijums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kriterijum.getId().intValue())))
            .andExpect(jsonPath("$.[*].kriterijumTip").value(hasItem(DEFAULT_KRITERIJUM_TIP.toString())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].ponder").value(hasItem(DEFAULT_PONDER.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getKriterijum() throws Exception {
        // Initialize the database
        kriterijumRepository.saveAndFlush(kriterijum);

        // Get the kriterijum
        restKriterijumMockMvc.perform(get("/api/kriterijums/{id}", kriterijum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kriterijum.getId().intValue()))
            .andExpect(jsonPath("$.kriterijumTip").value(DEFAULT_KRITERIJUM_TIP.toString()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.ponder").value(DEFAULT_PONDER.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKriterijum() throws Exception {
        // Get the kriterijum
        restKriterijumMockMvc.perform(get("/api/kriterijums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKriterijum() throws Exception {
        // Initialize the database
        kriterijumService.save(kriterijum);

        int databaseSizeBeforeUpdate = kriterijumRepository.findAll().size();

        // Update the kriterijum
        Kriterijum updatedKriterijum = kriterijumRepository.findById(kriterijum.getId()).get();
        // Disconnect from session so that the updates on updatedKriterijum are not directly saved in db
        em.detach(updatedKriterijum);
        updatedKriterijum
            .kriterijumTip(UPDATED_KRITERIJUM_TIP)
            .naziv(UPDATED_NAZIV)
            .ponder(UPDATED_PONDER);

        restKriterijumMockMvc.perform(put("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKriterijum)))
            .andExpect(status().isOk());

        // Validate the Kriterijum in the database
        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeUpdate);
        Kriterijum testKriterijum = kriterijumList.get(kriterijumList.size() - 1);
        assertThat(testKriterijum.getKriterijumTip()).isEqualTo(UPDATED_KRITERIJUM_TIP);
        assertThat(testKriterijum.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testKriterijum.getPonder()).isEqualTo(UPDATED_PONDER);
    }

    @Test
    @Transactional
    public void updateNonExistingKriterijum() throws Exception {
        int databaseSizeBeforeUpdate = kriterijumRepository.findAll().size();

        // Create the Kriterijum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKriterijumMockMvc.perform(put("/api/kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijum)))
            .andExpect(status().isBadRequest());

        // Validate the Kriterijum in the database
        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKriterijum() throws Exception {
        // Initialize the database
        kriterijumService.save(kriterijum);

        int databaseSizeBeforeDelete = kriterijumRepository.findAll().size();

        // Get the kriterijum
        restKriterijumMockMvc.perform(delete("/api/kriterijums/{id}", kriterijum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kriterijum> kriterijumList = kriterijumRepository.findAll();
        assertThat(kriterijumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kriterijum.class);
        Kriterijum kriterijum1 = new Kriterijum();
        kriterijum1.setId(1L);
        Kriterijum kriterijum2 = new Kriterijum();
        kriterijum2.setId(kriterijum1.getId());
        assertThat(kriterijum1).isEqualTo(kriterijum2);
        kriterijum2.setId(2L);
        assertThat(kriterijum1).isNotEqualTo(kriterijum2);
        kriterijum1.setId(null);
        assertThat(kriterijum1).isNotEqualTo(kriterijum2);
    }
}
