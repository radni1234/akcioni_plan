package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.AkcioniPlan;
import com.plan.vr.domain.User;
import com.plan.vr.repository.AkcioniPlanRepository;
import com.plan.vr.service.AkcioniPlanService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.plan.vr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AkcioniPlanResource REST controller.
 *
 * @see AkcioniPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class AkcioniPlanResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final Double DEFAULT_BUDZET = 1D;
    private static final Double UPDATED_BUDZET = 2D;

    private static final LocalDate DEFAULT_DATUM_OD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUM_DO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_DO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AkcioniPlanRepository akcioniPlanRepository;

    

    @Autowired
    private AkcioniPlanService akcioniPlanService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAkcioniPlanMockMvc;

    private AkcioniPlan akcioniPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AkcioniPlanResource akcioniPlanResource = new AkcioniPlanResource(akcioniPlanService);
        this.restAkcioniPlanMockMvc = MockMvcBuilders.standaloneSetup(akcioniPlanResource)
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
    public static AkcioniPlan createEntity(EntityManager em) {
        AkcioniPlan akcioniPlan = new AkcioniPlan()
            .naziv(DEFAULT_NAZIV)
            .opis(DEFAULT_OPIS)
            .budzet(DEFAULT_BUDZET)
            .datumOd(DEFAULT_DATUM_OD)
            .datumDo(DEFAULT_DATUM_DO);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        akcioniPlan.setUser(user);
        return akcioniPlan;
    }

    @Before
    public void initTest() {
        akcioniPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createAkcioniPlan() throws Exception {
        int databaseSizeBeforeCreate = akcioniPlanRepository.findAll().size();

        // Create the AkcioniPlan
        restAkcioniPlanMockMvc.perform(post("/api/akcioni-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(akcioniPlan)))
            .andExpect(status().isCreated());

        // Validate the AkcioniPlan in the database
        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeCreate + 1);
        AkcioniPlan testAkcioniPlan = akcioniPlanList.get(akcioniPlanList.size() - 1);
        assertThat(testAkcioniPlan.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testAkcioniPlan.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testAkcioniPlan.getBudzet()).isEqualTo(DEFAULT_BUDZET);
        assertThat(testAkcioniPlan.getDatumOd()).isEqualTo(DEFAULT_DATUM_OD);
        assertThat(testAkcioniPlan.getDatumDo()).isEqualTo(DEFAULT_DATUM_DO);
    }

    @Test
    @Transactional
    public void createAkcioniPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = akcioniPlanRepository.findAll().size();

        // Create the AkcioniPlan with an existing ID
        akcioniPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAkcioniPlanMockMvc.perform(post("/api/akcioni-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(akcioniPlan)))
            .andExpect(status().isBadRequest());

        // Validate the AkcioniPlan in the database
        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = akcioniPlanRepository.findAll().size();
        // set the field null
        akcioniPlan.setNaziv(null);

        // Create the AkcioniPlan, which fails.

        restAkcioniPlanMockMvc.perform(post("/api/akcioni-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(akcioniPlan)))
            .andExpect(status().isBadRequest());

        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAkcioniPlans() throws Exception {
        // Initialize the database
        akcioniPlanRepository.saveAndFlush(akcioniPlan);

        // Get all the akcioniPlanList
        restAkcioniPlanMockMvc.perform(get("/api/akcioni-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akcioniPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS.toString())))
            .andExpect(jsonPath("$.[*].budzet").value(hasItem(DEFAULT_BUDZET.doubleValue())))
            .andExpect(jsonPath("$.[*].datumOd").value(hasItem(DEFAULT_DATUM_OD.toString())))
            .andExpect(jsonPath("$.[*].datumDo").value(hasItem(DEFAULT_DATUM_DO.toString())));
    }
    

    @Test
    @Transactional
    public void getAkcioniPlan() throws Exception {
        // Initialize the database
        akcioniPlanRepository.saveAndFlush(akcioniPlan);

        // Get the akcioniPlan
        restAkcioniPlanMockMvc.perform(get("/api/akcioni-plans/{id}", akcioniPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(akcioniPlan.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS.toString()))
            .andExpect(jsonPath("$.budzet").value(DEFAULT_BUDZET.doubleValue()))
            .andExpect(jsonPath("$.datumOd").value(DEFAULT_DATUM_OD.toString()))
            .andExpect(jsonPath("$.datumDo").value(DEFAULT_DATUM_DO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAkcioniPlan() throws Exception {
        // Get the akcioniPlan
        restAkcioniPlanMockMvc.perform(get("/api/akcioni-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAkcioniPlan() throws Exception {
        // Initialize the database
        akcioniPlanService.save(akcioniPlan);

        int databaseSizeBeforeUpdate = akcioniPlanRepository.findAll().size();

        // Update the akcioniPlan
        AkcioniPlan updatedAkcioniPlan = akcioniPlanRepository.findById(akcioniPlan.getId()).get();
        // Disconnect from session so that the updates on updatedAkcioniPlan are not directly saved in db
        em.detach(updatedAkcioniPlan);
        updatedAkcioniPlan
            .naziv(UPDATED_NAZIV)
            .opis(UPDATED_OPIS)
            .budzet(UPDATED_BUDZET)
            .datumOd(UPDATED_DATUM_OD)
            .datumDo(UPDATED_DATUM_DO);

        restAkcioniPlanMockMvc.perform(put("/api/akcioni-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAkcioniPlan)))
            .andExpect(status().isOk());

        // Validate the AkcioniPlan in the database
        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeUpdate);
        AkcioniPlan testAkcioniPlan = akcioniPlanList.get(akcioniPlanList.size() - 1);
        assertThat(testAkcioniPlan.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testAkcioniPlan.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testAkcioniPlan.getBudzet()).isEqualTo(UPDATED_BUDZET);
        assertThat(testAkcioniPlan.getDatumOd()).isEqualTo(UPDATED_DATUM_OD);
        assertThat(testAkcioniPlan.getDatumDo()).isEqualTo(UPDATED_DATUM_DO);
    }

    @Test
    @Transactional
    public void updateNonExistingAkcioniPlan() throws Exception {
        int databaseSizeBeforeUpdate = akcioniPlanRepository.findAll().size();

        // Create the AkcioniPlan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAkcioniPlanMockMvc.perform(put("/api/akcioni-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(akcioniPlan)))
            .andExpect(status().isBadRequest());

        // Validate the AkcioniPlan in the database
        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAkcioniPlan() throws Exception {
        // Initialize the database
        akcioniPlanService.save(akcioniPlan);

        int databaseSizeBeforeDelete = akcioniPlanRepository.findAll().size();

        // Get the akcioniPlan
        restAkcioniPlanMockMvc.perform(delete("/api/akcioni-plans/{id}", akcioniPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AkcioniPlan> akcioniPlanList = akcioniPlanRepository.findAll();
        assertThat(akcioniPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkcioniPlan.class);
        AkcioniPlan akcioniPlan1 = new AkcioniPlan();
        akcioniPlan1.setId(1L);
        AkcioniPlan akcioniPlan2 = new AkcioniPlan();
        akcioniPlan2.setId(akcioniPlan1.getId());
        assertThat(akcioniPlan1).isEqualTo(akcioniPlan2);
        akcioniPlan2.setId(2L);
        assertThat(akcioniPlan1).isNotEqualTo(akcioniPlan2);
        akcioniPlan1.setId(null);
        assertThat(akcioniPlan1).isNotEqualTo(akcioniPlan2);
    }
}
