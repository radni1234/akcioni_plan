package com.plan.vr.web.rest;

import com.plan.vr.AkcioniPlanApp;

import com.plan.vr.domain.Projekat;
import com.plan.vr.domain.AkcioniPlan;
import com.plan.vr.repository.ProjekatRepository;
import com.plan.vr.service.ProjekatService;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the ProjekatResource REST controller.
 *
 * @see ProjekatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AkcioniPlanApp.class)
public class ProjekatResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM_OD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OD = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATUM_DO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_DO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AKTIVNOST = "AAAAAAAAAA";
    private static final String UPDATED_AKTIVNOST = "BBBBBBBBBB";

    private static final String DEFAULT_LOKACIJA = "AAAAAAAAAA";
    private static final String UPDATED_LOKACIJA = "BBBBBBBBBB";

    private static final String DEFAULT_OPSTI_CILJ = "AAAAAAAAAA";
    private static final String UPDATED_OPSTI_CILJ = "BBBBBBBBBB";

    private static final String DEFAULT_POSEBNI_CILJ = "AAAAAAAAAA";
    private static final String UPDATED_POSEBNI_CILJ = "BBBBBBBBBB";

    private static final String DEFAULT_INDIKATORI = "AAAAAAAAAA";
    private static final String UPDATED_INDIKATORI = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENTNI_PARAMETAR = "AAAAAAAAAA";
    private static final String UPDATED_REFERENTNI_PARAMETAR = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTICIJA = "AAAAAAAAAA";
    private static final String UPDATED_INVESTICIJA = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS_MERE = "AAAAAAAAAA";
    private static final String UPDATED_OPIS_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_ODGOVORNA_OSOBA = "AAAAAAAAAA";
    private static final String UPDATED_ODGOVORNA_OSOBA = "BBBBBBBBBB";

    private static final String DEFAULT_IZVOR_FINANSIRANJA = "AAAAAAAAAA";
    private static final String UPDATED_IZVOR_FINANSIRANJA = "BBBBBBBBBB";

    private static final String DEFAULT_OSTALO = "AAAAAAAAAA";
    private static final String UPDATED_OSTALO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SLIKA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SLIKA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SLIKA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SLIKA_CONTENT_TYPE = "image/png";

    @Autowired
    private ProjekatRepository projekatRepository;

    

    @Autowired
    private ProjekatService projekatService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjekatMockMvc;

    private Projekat projekat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjekatResource projekatResource = new ProjekatResource(projekatService);
        this.restProjekatMockMvc = MockMvcBuilders.standaloneSetup(projekatResource)
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
    public static Projekat createEntity(EntityManager em) {
        Projekat projekat = new Projekat()
            .naziv(DEFAULT_NAZIV)
            .datumOd(DEFAULT_DATUM_OD)
            .datumDo(DEFAULT_DATUM_DO)
            .aktivnost(DEFAULT_AKTIVNOST)
            .lokacija(DEFAULT_LOKACIJA)
            .opstiCilj(DEFAULT_OPSTI_CILJ)
            .posebniCilj(DEFAULT_POSEBNI_CILJ)
            .indikatori(DEFAULT_INDIKATORI)
            .referentniParametar(DEFAULT_REFERENTNI_PARAMETAR)
            .investicija(DEFAULT_INVESTICIJA)
            .opisMere(DEFAULT_OPIS_MERE)
            .odgovornaOsoba(DEFAULT_ODGOVORNA_OSOBA)
            .izvorFinansiranja(DEFAULT_IZVOR_FINANSIRANJA)
            .ostalo(DEFAULT_OSTALO)
            .slika(DEFAULT_SLIKA)
            .slikaContentType(DEFAULT_SLIKA_CONTENT_TYPE);
        // Add required entity
        AkcioniPlan akcioniPlan = AkcioniPlanResourceIntTest.createEntity(em);
        em.persist(akcioniPlan);
        em.flush();
        projekat.setAkcioniPlan(akcioniPlan);
        return projekat;
    }

    @Before
    public void initTest() {
        projekat = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjekat() throws Exception {
        int databaseSizeBeforeCreate = projekatRepository.findAll().size();

        // Create the Projekat
        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isCreated());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeCreate + 1);
        Projekat testProjekat = projekatList.get(projekatList.size() - 1);
        assertThat(testProjekat.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testProjekat.getDatumOd()).isEqualTo(DEFAULT_DATUM_OD);
        assertThat(testProjekat.getDatumDo()).isEqualTo(DEFAULT_DATUM_DO);
        assertThat(testProjekat.getAktivnost()).isEqualTo(DEFAULT_AKTIVNOST);
        assertThat(testProjekat.getLokacija()).isEqualTo(DEFAULT_LOKACIJA);
        assertThat(testProjekat.getOpstiCilj()).isEqualTo(DEFAULT_OPSTI_CILJ);
        assertThat(testProjekat.getPosebniCilj()).isEqualTo(DEFAULT_POSEBNI_CILJ);
        assertThat(testProjekat.getIndikatori()).isEqualTo(DEFAULT_INDIKATORI);
        assertThat(testProjekat.getReferentniParametar()).isEqualTo(DEFAULT_REFERENTNI_PARAMETAR);
        assertThat(testProjekat.getInvesticija()).isEqualTo(DEFAULT_INVESTICIJA);
        assertThat(testProjekat.getOpisMere()).isEqualTo(DEFAULT_OPIS_MERE);
        assertThat(testProjekat.getOdgovornaOsoba()).isEqualTo(DEFAULT_ODGOVORNA_OSOBA);
        assertThat(testProjekat.getIzvorFinansiranja()).isEqualTo(DEFAULT_IZVOR_FINANSIRANJA);
        assertThat(testProjekat.getOstalo()).isEqualTo(DEFAULT_OSTALO);
        assertThat(testProjekat.getSlika()).isEqualTo(DEFAULT_SLIKA);
        assertThat(testProjekat.getSlikaContentType()).isEqualTo(DEFAULT_SLIKA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createProjekatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projekatRepository.findAll().size();

        // Create the Projekat with an existing ID
        projekat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = projekatRepository.findAll().size();
        // set the field null
        projekat.setNaziv(null);

        // Create the Projekat, which fails.

        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjekats() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        // Get all the projekatList
        restProjekatMockMvc.perform(get("/api/projekats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projekat.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].datumOd").value(hasItem(DEFAULT_DATUM_OD.toString())))
            .andExpect(jsonPath("$.[*].datumDo").value(hasItem(DEFAULT_DATUM_DO.toString())))
            .andExpect(jsonPath("$.[*].aktivnost").value(hasItem(DEFAULT_AKTIVNOST.toString())))
            .andExpect(jsonPath("$.[*].lokacija").value(hasItem(DEFAULT_LOKACIJA.toString())))
            .andExpect(jsonPath("$.[*].opstiCilj").value(hasItem(DEFAULT_OPSTI_CILJ.toString())))
            .andExpect(jsonPath("$.[*].posebniCilj").value(hasItem(DEFAULT_POSEBNI_CILJ.toString())))
            .andExpect(jsonPath("$.[*].indikatori").value(hasItem(DEFAULT_INDIKATORI.toString())))
            .andExpect(jsonPath("$.[*].referentniParametar").value(hasItem(DEFAULT_REFERENTNI_PARAMETAR.toString())))
            .andExpect(jsonPath("$.[*].investicija").value(hasItem(DEFAULT_INVESTICIJA.toString())))
            .andExpect(jsonPath("$.[*].opisMere").value(hasItem(DEFAULT_OPIS_MERE.toString())))
            .andExpect(jsonPath("$.[*].odgovornaOsoba").value(hasItem(DEFAULT_ODGOVORNA_OSOBA.toString())))
            .andExpect(jsonPath("$.[*].izvorFinansiranja").value(hasItem(DEFAULT_IZVOR_FINANSIRANJA.toString())))
            .andExpect(jsonPath("$.[*].ostalo").value(hasItem(DEFAULT_OSTALO.toString())))
            .andExpect(jsonPath("$.[*].slikaContentType").value(hasItem(DEFAULT_SLIKA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].slika").value(hasItem(Base64Utils.encodeToString(DEFAULT_SLIKA))));
    }
    

    @Test
    @Transactional
    public void getProjekat() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        // Get the projekat
        restProjekatMockMvc.perform(get("/api/projekats/{id}", projekat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projekat.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.datumOd").value(DEFAULT_DATUM_OD.toString()))
            .andExpect(jsonPath("$.datumDo").value(DEFAULT_DATUM_DO.toString()))
            .andExpect(jsonPath("$.aktivnost").value(DEFAULT_AKTIVNOST.toString()))
            .andExpect(jsonPath("$.lokacija").value(DEFAULT_LOKACIJA.toString()))
            .andExpect(jsonPath("$.opstiCilj").value(DEFAULT_OPSTI_CILJ.toString()))
            .andExpect(jsonPath("$.posebniCilj").value(DEFAULT_POSEBNI_CILJ.toString()))
            .andExpect(jsonPath("$.indikatori").value(DEFAULT_INDIKATORI.toString()))
            .andExpect(jsonPath("$.referentniParametar").value(DEFAULT_REFERENTNI_PARAMETAR.toString()))
            .andExpect(jsonPath("$.investicija").value(DEFAULT_INVESTICIJA.toString()))
            .andExpect(jsonPath("$.opisMere").value(DEFAULT_OPIS_MERE.toString()))
            .andExpect(jsonPath("$.odgovornaOsoba").value(DEFAULT_ODGOVORNA_OSOBA.toString()))
            .andExpect(jsonPath("$.izvorFinansiranja").value(DEFAULT_IZVOR_FINANSIRANJA.toString()))
            .andExpect(jsonPath("$.ostalo").value(DEFAULT_OSTALO.toString()))
            .andExpect(jsonPath("$.slikaContentType").value(DEFAULT_SLIKA_CONTENT_TYPE))
            .andExpect(jsonPath("$.slika").value(Base64Utils.encodeToString(DEFAULT_SLIKA)));
    }
    @Test
    @Transactional
    public void getNonExistingProjekat() throws Exception {
        // Get the projekat
        restProjekatMockMvc.perform(get("/api/projekats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjekat() throws Exception {
        // Initialize the database
        projekatService.save(projekat);

        int databaseSizeBeforeUpdate = projekatRepository.findAll().size();

        // Update the projekat
        Projekat updatedProjekat = projekatRepository.findById(projekat.getId()).get();
        // Disconnect from session so that the updates on updatedProjekat are not directly saved in db
        em.detach(updatedProjekat);
        updatedProjekat
            .naziv(UPDATED_NAZIV)
            .datumOd(UPDATED_DATUM_OD)
            .datumDo(UPDATED_DATUM_DO)
            .aktivnost(UPDATED_AKTIVNOST)
            .lokacija(UPDATED_LOKACIJA)
            .opstiCilj(UPDATED_OPSTI_CILJ)
            .posebniCilj(UPDATED_POSEBNI_CILJ)
            .indikatori(UPDATED_INDIKATORI)
            .referentniParametar(UPDATED_REFERENTNI_PARAMETAR)
            .investicija(UPDATED_INVESTICIJA)
            .opisMere(UPDATED_OPIS_MERE)
            .odgovornaOsoba(UPDATED_ODGOVORNA_OSOBA)
            .izvorFinansiranja(UPDATED_IZVOR_FINANSIRANJA)
            .ostalo(UPDATED_OSTALO)
            .slika(UPDATED_SLIKA)
            .slikaContentType(UPDATED_SLIKA_CONTENT_TYPE);

        restProjekatMockMvc.perform(put("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjekat)))
            .andExpect(status().isOk());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeUpdate);
        Projekat testProjekat = projekatList.get(projekatList.size() - 1);
        assertThat(testProjekat.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testProjekat.getDatumOd()).isEqualTo(UPDATED_DATUM_OD);
        assertThat(testProjekat.getDatumDo()).isEqualTo(UPDATED_DATUM_DO);
        assertThat(testProjekat.getAktivnost()).isEqualTo(UPDATED_AKTIVNOST);
        assertThat(testProjekat.getLokacija()).isEqualTo(UPDATED_LOKACIJA);
        assertThat(testProjekat.getOpstiCilj()).isEqualTo(UPDATED_OPSTI_CILJ);
        assertThat(testProjekat.getPosebniCilj()).isEqualTo(UPDATED_POSEBNI_CILJ);
        assertThat(testProjekat.getIndikatori()).isEqualTo(UPDATED_INDIKATORI);
        assertThat(testProjekat.getReferentniParametar()).isEqualTo(UPDATED_REFERENTNI_PARAMETAR);
        assertThat(testProjekat.getInvesticija()).isEqualTo(UPDATED_INVESTICIJA);
        assertThat(testProjekat.getOpisMere()).isEqualTo(UPDATED_OPIS_MERE);
        assertThat(testProjekat.getOdgovornaOsoba()).isEqualTo(UPDATED_ODGOVORNA_OSOBA);
        assertThat(testProjekat.getIzvorFinansiranja()).isEqualTo(UPDATED_IZVOR_FINANSIRANJA);
        assertThat(testProjekat.getOstalo()).isEqualTo(UPDATED_OSTALO);
        assertThat(testProjekat.getSlika()).isEqualTo(UPDATED_SLIKA);
        assertThat(testProjekat.getSlikaContentType()).isEqualTo(UPDATED_SLIKA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjekat() throws Exception {
        int databaseSizeBeforeUpdate = projekatRepository.findAll().size();

        // Create the Projekat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjekatMockMvc.perform(put("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjekat() throws Exception {
        // Initialize the database
        projekatService.save(projekat);

        int databaseSizeBeforeDelete = projekatRepository.findAll().size();

        // Get the projekat
        restProjekatMockMvc.perform(delete("/api/projekats/{id}", projekat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projekat.class);
        Projekat projekat1 = new Projekat();
        projekat1.setId(1L);
        Projekat projekat2 = new Projekat();
        projekat2.setId(projekat1.getId());
        assertThat(projekat1).isEqualTo(projekat2);
        projekat2.setId(2L);
        assertThat(projekat1).isNotEqualTo(projekat2);
        projekat1.setId(null);
        assertThat(projekat1).isNotEqualTo(projekat2);
    }
}
