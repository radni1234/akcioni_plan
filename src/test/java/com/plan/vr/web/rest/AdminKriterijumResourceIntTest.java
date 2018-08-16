package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.AdminKriterijum;
import com.plan.vr.repository.AdminKriterijumRepository;
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
 * Test class for the AdminKriterijumResource REST controller.
 *
 * @see AdminKriterijumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class AdminKriterijumResourceIntTest {

    private static final KriterijumTip DEFAULT_KRITERIJUM_TIP = KriterijumTip.BOD;
    private static final KriterijumTip UPDATED_KRITERIJUM_TIP = KriterijumTip.VREDNOST;

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final Float DEFAULT_PONDER = 1F;
    private static final Float UPDATED_PONDER = 2F;

    @Autowired
    private AdminKriterijumRepository adminKriterijumRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAdminKriterijumMockMvc;

    private AdminKriterijum adminKriterijum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AdminKriterijumResource adminKriterijumResource = new AdminKriterijumResource(adminKriterijumRepository);
        this.restAdminKriterijumMockMvc = MockMvcBuilders.standaloneSetup(adminKriterijumResource)
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
    public static AdminKriterijum createEntity(EntityManager em) {
        AdminKriterijum adminKriterijum = new AdminKriterijum()
            .kriterijumTip(DEFAULT_KRITERIJUM_TIP)
            .naziv(DEFAULT_NAZIV)
            .ponder(DEFAULT_PONDER);
        return adminKriterijum;
    }

    @Before
    public void initTest() {
        adminKriterijum = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdminKriterijum() throws Exception {
        int databaseSizeBeforeCreate = adminKriterijumRepository.findAll().size();

        // Create the AdminKriterijum
        restAdminKriterijumMockMvc.perform(post("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isCreated());

        // Validate the AdminKriterijum in the database
        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeCreate + 1);
        AdminKriterijum testAdminKriterijum = adminKriterijumList.get(adminKriterijumList.size() - 1);
        assertThat(testAdminKriterijum.getKriterijumTip()).isEqualTo(DEFAULT_KRITERIJUM_TIP);
        assertThat(testAdminKriterijum.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testAdminKriterijum.getPonder()).isEqualTo(DEFAULT_PONDER);
    }

    @Test
    @Transactional
    public void createAdminKriterijumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminKriterijumRepository.findAll().size();

        // Create the AdminKriterijum with an existing ID
        adminKriterijum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminKriterijumMockMvc.perform(post("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isBadRequest());

        // Validate the AdminKriterijum in the database
        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKriterijumTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumRepository.findAll().size();
        // set the field null
        adminKriterijum.setKriterijumTip(null);

        // Create the AdminKriterijum, which fails.

        restAdminKriterijumMockMvc.perform(post("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumRepository.findAll().size();
        // set the field null
        adminKriterijum.setNaziv(null);

        // Create the AdminKriterijum, which fails.

        restAdminKriterijumMockMvc.perform(post("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPonderIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminKriterijumRepository.findAll().size();
        // set the field null
        adminKriterijum.setPonder(null);

        // Create the AdminKriterijum, which fails.

        restAdminKriterijumMockMvc.perform(post("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isBadRequest());

        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdminKriterijums() throws Exception {
        // Initialize the database
        adminKriterijumRepository.saveAndFlush(adminKriterijum);

        // Get all the adminKriterijumList
        restAdminKriterijumMockMvc.perform(get("/api/admin-kriterijums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminKriterijum.getId().intValue())))
            .andExpect(jsonPath("$.[*].kriterijumTip").value(hasItem(DEFAULT_KRITERIJUM_TIP.toString())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].ponder").value(hasItem(DEFAULT_PONDER.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getAdminKriterijum() throws Exception {
        // Initialize the database
        adminKriterijumRepository.saveAndFlush(adminKriterijum);

        // Get the adminKriterijum
        restAdminKriterijumMockMvc.perform(get("/api/admin-kriterijums/{id}", adminKriterijum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(adminKriterijum.getId().intValue()))
            .andExpect(jsonPath("$.kriterijumTip").value(DEFAULT_KRITERIJUM_TIP.toString()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.ponder").value(DEFAULT_PONDER.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAdminKriterijum() throws Exception {
        // Get the adminKriterijum
        restAdminKriterijumMockMvc.perform(get("/api/admin-kriterijums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdminKriterijum() throws Exception {
        // Initialize the database
        adminKriterijumRepository.saveAndFlush(adminKriterijum);

        int databaseSizeBeforeUpdate = adminKriterijumRepository.findAll().size();

        // Update the adminKriterijum
        AdminKriterijum updatedAdminKriterijum = adminKriterijumRepository.findById(adminKriterijum.getId()).get();
        // Disconnect from session so that the updates on updatedAdminKriterijum are not directly saved in db
        em.detach(updatedAdminKriterijum);
        updatedAdminKriterijum
            .kriterijumTip(UPDATED_KRITERIJUM_TIP)
            .naziv(UPDATED_NAZIV)
            .ponder(UPDATED_PONDER);

        restAdminKriterijumMockMvc.perform(put("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdminKriterijum)))
            .andExpect(status().isOk());

        // Validate the AdminKriterijum in the database
        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeUpdate);
        AdminKriterijum testAdminKriterijum = adminKriterijumList.get(adminKriterijumList.size() - 1);
        assertThat(testAdminKriterijum.getKriterijumTip()).isEqualTo(UPDATED_KRITERIJUM_TIP);
        assertThat(testAdminKriterijum.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testAdminKriterijum.getPonder()).isEqualTo(UPDATED_PONDER);
    }

    @Test
    @Transactional
    public void updateNonExistingAdminKriterijum() throws Exception {
        int databaseSizeBeforeUpdate = adminKriterijumRepository.findAll().size();

        // Create the AdminKriterijum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAdminKriterijumMockMvc.perform(put("/api/admin-kriterijums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(adminKriterijum)))
            .andExpect(status().isBadRequest());

        // Validate the AdminKriterijum in the database
        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdminKriterijum() throws Exception {
        // Initialize the database
        adminKriterijumRepository.saveAndFlush(adminKriterijum);

        int databaseSizeBeforeDelete = adminKriterijumRepository.findAll().size();

        // Get the adminKriterijum
        restAdminKriterijumMockMvc.perform(delete("/api/admin-kriterijums/{id}", adminKriterijum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AdminKriterijum> adminKriterijumList = adminKriterijumRepository.findAll();
        assertThat(adminKriterijumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminKriterijum.class);
        AdminKriterijum adminKriterijum1 = new AdminKriterijum();
        adminKriterijum1.setId(1L);
        AdminKriterijum adminKriterijum2 = new AdminKriterijum();
        adminKriterijum2.setId(adminKriterijum1.getId());
        assertThat(adminKriterijum1).isEqualTo(adminKriterijum2);
        adminKriterijum2.setId(2L);
        assertThat(adminKriterijum1).isNotEqualTo(adminKriterijum2);
        adminKriterijum1.setId(null);
        assertThat(adminKriterijum1).isNotEqualTo(adminKriterijum2);
    }
}
