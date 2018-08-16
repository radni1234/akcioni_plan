package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.AdminKriterijumBodovanje;
import com.plan.vr.domain.AdminKriterijum;
import com.plan.vr.repository.AdminKriterijumBodovanjeRepository;
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
 * Test class for the AdminKriterijumBodovanjeResource REST controller.
 *
 * @see AdminKriterijumBodovanjeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class AdminKriterijumBodovanjeResourceIntTest {

    private static final Double DEFAULT_GRANICA_OD = 1D;
    private static final Double UPDATED_GRANICA_OD = 2D;

    private static final Double DEFAULT_GRANICA_DO = 1D;
    private static final Double UPDATED_GRANICA_DO = 2D;

    private static final Double DEFAULT_BODOVI = 1D;
    private static final Double UPDATED_BODOVI = 2D;

    @Autowired
    private AdminKriterijumBodovanjeRepository adminKriterijumBodovanjeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdminKriterijumBodovanjeMockMvc;

    private AdminKriterijumBodovanje adminKriterijumBodovanje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdminKriterijumBodovanjeResource adminKriterijumBodovanjeResource = new AdminKriterijumBodovanjeResource(adminKriterijumBodovanjeRepository);
        this.restAdminKriterijumBodovanjeMockMvc = MockMvcBuilders.standaloneSetup(adminKriterijumBodovanjeResource)
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
    public static AdminKriterijumBodovanje createEntity(EntityManager em) {
        AdminKriterijumBodovanje adminKriterijumBodovanje = new AdminKriterijumBodovanje()
            .granicaOd(DEFAULT_GRANICA_OD)
            .granicaDo(DEFAULT_GRANICA_DO)
            .bodovi(DEFAULT_BODOVI);
        // Add required entity
        AdminKriterijum adminKriterijum = AdminKriterijumResourceIntTest.createEntity(em);
        em.persist(adminKriterijum);
        em.flush();
        adminKriterijumBodovanje.setAdminKriterijum(adminKriterijum);
        return adminKriterijumBodovanje;
    }

    @Before
    public void initTest() {
        adminKriterijumBodovanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminKriterijumBodovanje() throws Exception {
        int databaseSizeBeforeCreate = adminKriterijumBodovanjeRepository.findAll().size();

        // Create the AdminKriterijumBodovanje
        restAdminKriterijumBodovanjeMockMvc.perform(post("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isCreated());

        // Validate the AdminKriterijumBodovanje in the database
        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeCreate + 1);
        AdminKriterijumBodovanje testAdminKriterijumBodovanje = adminKriterijumBodovanjeList.get(adminKriterijumBodovanjeList.size() - 1);
        assertThat(testAdminKriterijumBodovanje.getGranicaOd()).isEqualTo(DEFAULT_GRANICA_OD);
        assertThat(testAdminKriterijumBodovanje.getGranicaDo()).isEqualTo(DEFAULT_GRANICA_DO);
        assertThat(testAdminKriterijumBodovanje.getBodovi()).isEqualTo(DEFAULT_BODOVI);
    }

    @Test
    @Transactional
    public void createAdminKriterijumBodovanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminKriterijumBodovanjeRepository.findAll().size();

        // Create the AdminKriterijumBodovanje with an existing ID
        adminKriterijumBodovanje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminKriterijumBodovanjeMockMvc.perform(post("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the AdminKriterijumBodovanje in the database
        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGranicaOdIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumBodovanjeRepository.findAll().size();
        // set the field null
        adminKriterijumBodovanje.setGranicaOd(null);

        // Create the AdminKriterijumBodovanje, which fails.

        restAdminKriterijumBodovanjeMockMvc.perform(post("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGranicaDoIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumBodovanjeRepository.findAll().size();
        // set the field null
        adminKriterijumBodovanje.setGranicaDo(null);

        // Create the AdminKriterijumBodovanje, which fails.

        restAdminKriterijumBodovanjeMockMvc.perform(post("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBodoviIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumBodovanjeRepository.findAll().size();
        // set the field null
        adminKriterijumBodovanje.setBodovi(null);

        // Create the AdminKriterijumBodovanje, which fails.

        restAdminKriterijumBodovanjeMockMvc.perform(post("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdminKriterijumBodovanjes() throws Exception {
        // Initialize the database
        adminKriterijumBodovanjeRepository.saveAndFlush(adminKriterijumBodovanje);

        // Get all the adminKriterijumBodovanjeList
        restAdminKriterijumBodovanjeMockMvc.perform(get("/api/admin-kriterijum-bodovanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminKriterijumBodovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].granicaOd").value(hasItem(DEFAULT_GRANICA_OD.doubleValue())))
            .andExpect(jsonPath("$.[*].granicaDo").value(hasItem(DEFAULT_GRANICA_DO.doubleValue())))
            .andExpect(jsonPath("$.[*].bodovi").value(hasItem(DEFAULT_BODOVI.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getAdminKriterijumBodovanje() throws Exception {
        // Initialize the database
        adminKriterijumBodovanjeRepository.saveAndFlush(adminKriterijumBodovanje);

        // Get the adminKriterijumBodovanje
        restAdminKriterijumBodovanjeMockMvc.perform(get("/api/admin-kriterijum-bodovanjes/{id}", adminKriterijumBodovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adminKriterijumBodovanje.getId().intValue()))
            .andExpect(jsonPath("$.granicaOd").value(DEFAULT_GRANICA_OD.doubleValue()))
            .andExpect(jsonPath("$.granicaDo").value(DEFAULT_GRANICA_DO.doubleValue()))
            .andExpect(jsonPath("$.bodovi").value(DEFAULT_BODOVI.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAdminKriterijumBodovanje() throws Exception {
        // Get the adminKriterijumBodovanje
        restAdminKriterijumBodovanjeMockMvc.perform(get("/api/admin-kriterijum-bodovanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminKriterijumBodovanje() throws Exception {
        // Initialize the database
        adminKriterijumBodovanjeRepository.saveAndFlush(adminKriterijumBodovanje);

        int databaseSizeBeforeUpdate = adminKriterijumBodovanjeRepository.findAll().size();

        // Update the adminKriterijumBodovanje
        AdminKriterijumBodovanje updatedAdminKriterijumBodovanje = adminKriterijumBodovanjeRepository.findById(adminKriterijumBodovanje.getId()).get();
        // Disconnect from session so that the updates on updatedAdminKriterijumBodovanje are not directly saved in db
        em.detach(updatedAdminKriterijumBodovanje);
        updatedAdminKriterijumBodovanje
            .granicaOd(UPDATED_GRANICA_OD)
            .granicaDo(UPDATED_GRANICA_DO)
            .bodovi(UPDATED_BODOVI);

        restAdminKriterijumBodovanjeMockMvc.perform(put("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdminKriterijumBodovanje)))
            .andExpect(status().isOk());

        // Validate the AdminKriterijumBodovanje in the database
        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeUpdate);
        AdminKriterijumBodovanje testAdminKriterijumBodovanje = adminKriterijumBodovanjeList.get(adminKriterijumBodovanjeList.size() - 1);
        assertThat(testAdminKriterijumBodovanje.getGranicaOd()).isEqualTo(UPDATED_GRANICA_OD);
        assertThat(testAdminKriterijumBodovanje.getGranicaDo()).isEqualTo(UPDATED_GRANICA_DO);
        assertThat(testAdminKriterijumBodovanje.getBodovi()).isEqualTo(UPDATED_BODOVI);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminKriterijumBodovanje() throws Exception {
        int databaseSizeBeforeUpdate = adminKriterijumBodovanjeRepository.findAll().size();

        // Create the AdminKriterijumBodovanje

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdminKriterijumBodovanjeMockMvc.perform(put("/api/admin-kriterijum-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijumBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the AdminKriterijumBodovanje in the database
        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdminKriterijumBodovanje() throws Exception {
        // Initialize the database
        adminKriterijumBodovanjeRepository.saveAndFlush(adminKriterijumBodovanje);

        int databaseSizeBeforeDelete = adminKriterijumBodovanjeRepository.findAll().size();

        // Get the adminKriterijumBodovanje
        restAdminKriterijumBodovanjeMockMvc.perform(delete("/api/admin-kriterijum-bodovanjes/{id}", adminKriterijumBodovanje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdminKriterijumBodovanje> adminKriterijumBodovanjeList = adminKriterijumBodovanjeRepository.findAll();
        assertThat(adminKriterijumBodovanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminKriterijumBodovanje.class);
        AdminKriterijumBodovanje adminKriterijumBodovanje1 = new AdminKriterijumBodovanje();
        adminKriterijumBodovanje1.setId(1L);
        AdminKriterijumBodovanje adminKriterijumBodovanje2 = new AdminKriterijumBodovanje();
        adminKriterijumBodovanje2.setId(adminKriterijumBodovanje1.getId());
        assertThat(adminKriterijumBodovanje1).isEqualTo(adminKriterijumBodovanje2);
        adminKriterijumBodovanje2.setId(2L);
        assertThat(adminKriterijumBodovanje1).isNotEqualTo(adminKriterijumBodovanje2);
        adminKriterijumBodovanje1.setId(null);
        assertThat(adminKriterijumBodovanje1).isNotEqualTo(adminKriterijumBodovanje2);
    }
}
