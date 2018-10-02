package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.KriterijumBodovanje;
import com.plan.vr.domain.Kriterijum;
import com.plan.vr.repository.KriterijumBodovanjeRepository;
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

/**
 * Test class for the KriterijumBodovanjeResource REST controller.
 *
 * @see KriterijumBodovanjeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class KriterijumBodovanjeResourceIntTest {

    private static final Double DEFAULT_GRANICA = 1D;
    private static final Double UPDATED_GRANICA = 2D;

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final Double DEFAULT_BODOVI = 1D;
    private static final Double UPDATED_BODOVI = 2D;

    @Autowired
    private KriterijumBodovanjeRepository kriterijumBodovanjeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKriterijumBodovanjeMockMvc;

    private KriterijumBodovanje kriterijumBodovanje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KriterijumBodovanjeResource kriterijumBodovanjeResource = new KriterijumBodovanjeResource(kriterijumBodovanjeRepository);
        this.restKriterijumBodovanjeMockMvc = MockMvcBuilders.standaloneSetup(kriterijumBodovanjeResource)
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
    public static KriterijumBodovanje createEntity(EntityManager em) {
        KriterijumBodovanje kriterijumBodovanje = new KriterijumBodovanje()
            .granica(DEFAULT_GRANICA)
            .opis(DEFAULT_OPIS)
            .bodovi(DEFAULT_BODOVI);
        // Add required entity
        Kriterijum kriterijum = KriterijumResourceIntTest.createEntity(em);
        em.persist(kriterijum);
        em.flush();
        kriterijumBodovanje.setKriterijum(kriterijum);
        return kriterijumBodovanje;
    }

    @Before
    public void initTest() {
        kriterijumBodovanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createKriterijumBodovanje() throws Exception {
        int databaseSizeBeforeCreate = kriterijumBodovanjeRepository.findAll().size();

        // Create the KriterijumBodovanje
        restKriterijumBodovanjeMockMvc.perform(post("/api/kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijumBodovanje)))
            .andExpect(status().isCreated());

        // Validate the KriterijumBodovanje in the database
        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeCreate + 1);
        KriterijumBodovanje testKriterijumBodovanje = kriterijumBodovanjeList.get(kriterijumBodovanjeList.size() - 1);
        assertThat(testKriterijumBodovanje.getGranica()).isEqualTo(DEFAULT_GRANICA);
        assertThat(testKriterijumBodovanje.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testKriterijumBodovanje.getBodovi()).isEqualTo(DEFAULT_BODOVI);
    }

    @Test
    @Transactional
    public void createKriterijumBodovanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kriterijumBodovanjeRepository.findAll().size();

        // Create the KriterijumBodovanje with an existing ID
        kriterijumBodovanje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKriterijumBodovanjeMockMvc.perform(post("/api/kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the KriterijumBodovanje in the database
        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBodoviIsRequired() throws Exception {
        int databaseSizeBeforeTest = kriterijumBodovanjeRepository.findAll().size();
        // set the field null
        kriterijumBodovanje.setBodovi(null);

        // Create the KriterijumBodovanje, which fails.

        restKriterijumBodovanjeMockMvc.perform(post("/api/kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKriterijumBodovanjes() throws Exception {
        // Initialize the database
        kriterijumBodovanjeRepository.saveAndFlush(kriterijumBodovanje);

        // Get all the kriterijumBodovanjeList
        restKriterijumBodovanjeMockMvc.perform(get("/api/kriterijum-bodovanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kriterijumBodovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].granica").value(hasItem(DEFAULT_GRANICA.doubleValue())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.toString())))
            .andExpect(jsonPath("$.[*].bodovi").value(hasItem(DEFAULT_BODOVI.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getKriterijumBodovanje() throws Exception {
        // Initialize the database
        kriterijumBodovanjeRepository.saveAndFlush(kriterijumBodovanje);

        // Get the kriterijumBodovanje
        restKriterijumBodovanjeMockMvc.perform(get("/api/kriterijum-bodovanjes/{id}", kriterijumBodovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kriterijumBodovanje.getId().intValue()))
            .andExpect(jsonPath("$.granica").value(DEFAULT_GRANICA.doubleValue()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.toString()))
            .andExpect(jsonPath("$.bodovi").value(DEFAULT_BODOVI.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKriterijumBodovanje() throws Exception {
        // Get the kriterijumBodovanje
        restKriterijumBodovanjeMockMvc.perform(get("/api/kriterijum-bodovanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKriterijumBodovanje() throws Exception {
        // Initialize the database
        kriterijumBodovanjeRepository.saveAndFlush(kriterijumBodovanje);

        int databaseSizeBeforeUpdate = kriterijumBodovanjeRepository.findAll().size();

        // Update the kriterijumBodovanje
        KriterijumBodovanje updatedKriterijumBodovanje = kriterijumBodovanjeRepository.findById(kriterijumBodovanje.getId()).get();
        // Disconnect from session so that the updates on updatedKriterijumBodovanje are not directly saved in db
        em.detach(updatedKriterijumBodovanje);
        updatedKriterijumBodovanje
            .granica(UPDATED_GRANICA)
            .opis(UPDATED_OPIS)
            .bodovi(UPDATED_BODOVI);

        restKriterijumBodovanjeMockMvc.perform(put("/api/kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKriterijumBodovanje)))
            .andExpect(status().isOk());

        // Validate the KriterijumBodovanje in the database
        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeUpdate);
        KriterijumBodovanje testKriterijumBodovanje = kriterijumBodovanjeList.get(kriterijumBodovanjeList.size() - 1);
        assertThat(testKriterijumBodovanje.getGranica()).isEqualTo(UPDATED_GRANICA);
        assertThat(testKriterijumBodovanje.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testKriterijumBodovanje.getBodovi()).isEqualTo(UPDATED_BODOVI);
    }

    @Test
    @Transactional
    public void updateNonExistingKriterijumBodovanje() throws Exception {
        int databaseSizeBeforeUpdate = kriterijumBodovanjeRepository.findAll().size();

        // Create the KriterijumBodovanje

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKriterijumBodovanjeMockMvc.perform(put("/api/kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the KriterijumBodovanje in the database
        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKriterijumBodovanje() throws Exception {
        // Initialize the database
        kriterijumBodovanjeRepository.saveAndFlush(kriterijumBodovanje);

        int databaseSizeBeforeDelete = kriterijumBodovanjeRepository.findAll().size();

        // Get the kriterijumBodovanje
        restKriterijumBodovanjeMockMvc.perform(delete("/api/kriterijum-bodovanjes/{id}", kriterijumBodovanje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KriterijumBodovanje> kriterijumBodovanjeList = kriterijumBodovanjeRepository.findAll();
        assertThat(kriterijumBodovanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KriterijumBodovanje.class);
        KriterijumBodovanje kriterijumBodovanje1 = new KriterijumBodovanje();
        kriterijumBodovanje1.setId(1L);
        KriterijumBodovanje kriterijumBodovanje2 = new KriterijumBodovanje();
        kriterijumBodovanje2.setId(kriterijumBodovanje1.getId());
        assertThat(kriterijumBodovanje1).isEqualTo(kriterijumBodovanje2);
        kriterijumBodovanje2.setId(2L);
        assertThat(kriterijumBodovanje1).isNotEqualTo(kriterijumBodovanje2);
        kriterijumBodovanje1.setId(null);
        assertThat(kriterijumBodovanje1).isNotEqualTo(kriterijumBodovanje2);
    }
}
