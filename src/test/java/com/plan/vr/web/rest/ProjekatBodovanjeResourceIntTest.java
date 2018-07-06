package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.ProjekatBodovanje;
import com.plan.vr.domain.Kriterijum;
import com.plan.vr.domain.Projekat;
import com.plan.vr.repository.ProjekatBodovanjeRepository;
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
 * Test class for the ProjekatBodovanjeResource REST controller.
 *
 * @see ProjekatBodovanjeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class ProjekatBodovanjeResourceIntTest {

    private static final Double DEFAULT_VREDNOST = 1D;
    private static final Double UPDATED_VREDNOST = 2D;

    private static final Double DEFAULT_BODOVI = 1D;
    private static final Double UPDATED_BODOVI = 2D;

    private static final Double DEFAULT_PONDER = 1D;
    private static final Double UPDATED_PONDER = 2D;

    private static final Double DEFAULT_PONDERISANI_BODOVI = 1D;
    private static final Double UPDATED_PONDERISANI_BODOVI = 2D;

    @Autowired
    private ProjekatBodovanjeRepository projekatBodovanjeRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjekatBodovanjeMockMvc;

    private ProjekatBodovanje projekatBodovanje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjekatBodovanjeResource projekatBodovanjeResource = new ProjekatBodovanjeResource(projekatBodovanjeRepository);
        this.restProjekatBodovanjeMockMvc = MockMvcBuilders.standaloneSetup(projekatBodovanjeResource)
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
    public static ProjekatBodovanje createEntity(EntityManager em) {
        ProjekatBodovanje projekatBodovanje = new ProjekatBodovanje()
            .vrednost(DEFAULT_VREDNOST)
            .bodovi(DEFAULT_BODOVI)
            .ponder(DEFAULT_PONDER)
            .ponderisaniBodovi(DEFAULT_PONDERISANI_BODOVI);
        // Add required entity
        Kriterijum kriterijum = KriterijumResourceIntTest.createEntity(em);
        em.persist(kriterijum);
        em.flush();
        projekatBodovanje.setKriterijum(kriterijum);
        // Add required entity
        Projekat projekat = ProjekatResourceIntTest.createEntity(em);
        em.persist(projekat);
        em.flush();
        projekatBodovanje.setProjekat(projekat);
        return projekatBodovanje;
    }

    @Before
    public void initTest() {
        projekatBodovanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjekatBodovanje() throws Exception {
        int databaseSizeBeforeCreate = projekatBodovanjeRepository.findAll().size();

        // Create the ProjekatBodovanje
        restProjekatBodovanjeMockMvc.perform(post("/api/projekat-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekatBodovanje)))
            .andExpect(status().isCreated());

        // Validate the ProjekatBodovanje in the database
        List<ProjekatBodovanje> projekatBodovanjeList = projekatBodovanjeRepository.findAll();
        assertThat(projekatBodovanjeList).hasSize(databaseSizeBeforeCreate + 1);
        ProjekatBodovanje testProjekatBodovanje = projekatBodovanjeList.get(projekatBodovanjeList.size() - 1);
        assertThat(testProjekatBodovanje.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testProjekatBodovanje.getBodovi()).isEqualTo(DEFAULT_BODOVI);
        assertThat(testProjekatBodovanje.getPonder()).isEqualTo(DEFAULT_PONDER);
        assertThat(testProjekatBodovanje.getPonderisaniBodovi()).isEqualTo(DEFAULT_PONDERISANI_BODOVI);
    }

    @Test
    @Transactional
    public void createProjekatBodovanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projekatBodovanjeRepository.findAll().size();

        // Create the ProjekatBodovanje with an existing ID
        projekatBodovanje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjekatBodovanjeMockMvc.perform(post("/api/projekat-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekatBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the ProjekatBodovanje in the database
        List<ProjekatBodovanje> projekatBodovanjeList = projekatBodovanjeRepository.findAll();
        assertThat(projekatBodovanjeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjekatBodovanjes() throws Exception {
        // Initialize the database
        projekatBodovanjeRepository.saveAndFlush(projekatBodovanje);

        // Get all the projekatBodovanjeList
        restProjekatBodovanjeMockMvc.perform(get("/api/projekat-bodovanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projekatBodovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].bodovi").value(hasItem(DEFAULT_BODOVI.doubleValue())))
            .andExpect(jsonPath("$.[*].ponder").value(hasItem(DEFAULT_PONDER.doubleValue())))
            .andExpect(jsonPath("$.[*].ponderisaniBodovi").value(hasItem(DEFAULT_PONDERISANI_BODOVI.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getProjekatBodovanje() throws Exception {
        // Initialize the database
        projekatBodovanjeRepository.saveAndFlush(projekatBodovanje);

        // Get the projekatBodovanje
        restProjekatBodovanjeMockMvc.perform(get("/api/projekat-bodovanjes/{id}", projekatBodovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projekatBodovanje.getId().intValue()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.doubleValue()))
            .andExpect(jsonPath("$.bodovi").value(DEFAULT_BODOVI.doubleValue()))
            .andExpect(jsonPath("$.ponder").value(DEFAULT_PONDER.doubleValue()))
            .andExpect(jsonPath("$.ponderisaniBodovi").value(DEFAULT_PONDERISANI_BODOVI.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProjekatBodovanje() throws Exception {
        // Get the projekatBodovanje
        restProjekatBodovanjeMockMvc.perform(get("/api/projekat-bodovanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjekatBodovanje() throws Exception {
        // Initialize the database
        projekatBodovanjeRepository.saveAndFlush(projekatBodovanje);

        int databaseSizeBeforeUpdate = projekatBodovanjeRepository.findAll().size();

        // Update the projekatBodovanje
        ProjekatBodovanje updatedProjekatBodovanje = projekatBodovanjeRepository.findById(projekatBodovanje.getId()).get();
        // Disconnect from session so that the updates on updatedProjekatBodovanje are not directly saved in db
        em.detach(updatedProjekatBodovanje);
        updatedProjekatBodovanje
            .vrednost(UPDATED_VREDNOST)
            .bodovi(UPDATED_BODOVI)
            .ponder(UPDATED_PONDER)
            .ponderisaniBodovi(UPDATED_PONDERISANI_BODOVI);

        restProjekatBodovanjeMockMvc.perform(put("/api/projekat-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjekatBodovanje)))
            .andExpect(status().isOk());

        // Validate the ProjekatBodovanje in the database
        List<ProjekatBodovanje> projekatBodovanjeList = projekatBodovanjeRepository.findAll();
        assertThat(projekatBodovanjeList).hasSize(databaseSizeBeforeUpdate);
        ProjekatBodovanje testProjekatBodovanje = projekatBodovanjeList.get(projekatBodovanjeList.size() - 1);
        assertThat(testProjekatBodovanje.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testProjekatBodovanje.getBodovi()).isEqualTo(UPDATED_BODOVI);
        assertThat(testProjekatBodovanje.getPonder()).isEqualTo(UPDATED_PONDER);
        assertThat(testProjekatBodovanje.getPonderisaniBodovi()).isEqualTo(UPDATED_PONDERISANI_BODOVI);
    }

    @Test
    @Transactional
    public void updateNonExistingProjekatBodovanje() throws Exception {
        int databaseSizeBeforeUpdate = projekatBodovanjeRepository.findAll().size();

        // Create the ProjekatBodovanje

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjekatBodovanjeMockMvc.perform(put("/api/projekat-bodovanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekatBodovanje)))
            .andExpect(status().isBadRequest());

        // Validate the ProjekatBodovanje in the database
        List<ProjekatBodovanje> projekatBodovanjeList = projekatBodovanjeRepository.findAll();
        assertThat(projekatBodovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjekatBodovanje() throws Exception {
        // Initialize the database
        projekatBodovanjeRepository.saveAndFlush(projekatBodovanje);

        int databaseSizeBeforeDelete = projekatBodovanjeRepository.findAll().size();

        // Get the projekatBodovanje
        restProjekatBodovanjeMockMvc.perform(delete("/api/projekat-bodovanjes/{id}", projekatBodovanje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjekatBodovanje> projekatBodovanjeList = projekatBodovanjeRepository.findAll();
        assertThat(projekatBodovanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjekatBodovanje.class);
        ProjekatBodovanje projekatBodovanje1 = new ProjekatBodovanje();
        projekatBodovanje1.setId(1L);
        ProjekatBodovanje projekatBodovanje2 = new ProjekatBodovanje();
        projekatBodovanje2.setId(projekatBodovanje1.getId());
        assertThat(projekatBodovanje1).isEqualTo(projekatBodovanje2);
        projekatBodovanje2.setId(2L);
        assertThat(projekatBodovanje1).isNotEqualTo(projekatBodovanje2);
        projekatBodovanje1.setId(null);
        assertThat(projekatBodovanje1).isNotEqualTo(projekatBodovanje2);
    }
}
