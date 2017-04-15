package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.repository.TaxClassRepository;
import com.smartshop.eshop.service.TaxClassService;
import com.smartshop.eshop.repository.search.TaxClassSearchRepository;
import com.smartshop.eshop.web.rest.errors.ExceptionTranslator;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaxClassResource REST controller.
 *
 * @see TaxClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class TaxClassControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private TaxClassRepository taxClassRepository;

    @Autowired
    private TaxClassService taxClassService;

    @Autowired
    private TaxClassSearchRepository taxClassSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaxClassMockMvc;

    private TaxClass taxClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxClassController taxClassController = new TaxClassController(taxClassService);
        this.restTaxClassMockMvc = MockMvcBuilders.standaloneSetup(taxClassController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxClass createEntity(EntityManager em) {
        TaxClass taxClass = new TaxClass()
            .title(DEFAULT_TITLE)
            .code(DEFAULT_CODE);
        return taxClass;
    }

    @Before
    public void initTest() {
        taxClassSearchRepository.deleteAll();
        taxClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxClass() throws Exception {
        int databaseSizeBeforeCreate = taxClassRepository.findAll().size();

        // Create the TaxClass
        restTaxClassMockMvc.perform(post("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxClass)))
            .andExpect(status().isCreated());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeCreate + 1);
        TaxClass testTaxClass = taxClassList.get(taxClassList.size() - 1);
        assertThat(testTaxClass.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTaxClass.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the TaxClass in Elasticsearch
        TaxClass taxClassEs = taxClassSearchRepository.findOne(testTaxClass.getId());
        assertThat(taxClassEs).isEqualToComparingFieldByField(testTaxClass);
    }

    @Test
    @Transactional
    public void createTaxClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxClassRepository.findAll().size();

        // Create the TaxClass with an existing ID
        taxClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxClassMockMvc.perform(post("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxClass)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxClassRepository.findAll().size();
        // set the field null
        taxClass.setTitle(null);

        // Create the TaxClass, which fails.

        restTaxClassMockMvc.perform(post("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxClass)))
            .andExpect(status().isBadRequest());

        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxClassRepository.findAll().size();
        // set the field null
        taxClass.setCode(null);

        // Create the TaxClass, which fails.

        restTaxClassMockMvc.perform(post("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxClass)))
            .andExpect(status().isBadRequest());

        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxClasses() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get all the taxClassList
        restTaxClassMockMvc.perform(get("/api/tax-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getTaxClass() throws Exception {
        // Initialize the database
        taxClassRepository.saveAndFlush(taxClass);

        // Get the taxClass
        restTaxClassMockMvc.perform(get("/api/tax-classes/{id}", taxClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxClass.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxClass() throws Exception {
        // Get the taxClass
        restTaxClassMockMvc.perform(get("/api/tax-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxClass() throws Exception {
        // Initialize the database
        taxClassService.save(taxClass);

        int databaseSizeBeforeUpdate = taxClassRepository.findAll().size();

        // Update the taxClass
        TaxClass updatedTaxClass = taxClassRepository.findOne(taxClass.getId());
        updatedTaxClass
            .title(UPDATED_TITLE)
            .code(UPDATED_CODE);

        restTaxClassMockMvc.perform(put("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxClass)))
            .andExpect(status().isOk());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeUpdate);
        TaxClass testTaxClass = taxClassList.get(taxClassList.size() - 1);
        assertThat(testTaxClass.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTaxClass.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the TaxClass in Elasticsearch
        TaxClass taxClassEs = taxClassSearchRepository.findOne(testTaxClass.getId());
        assertThat(taxClassEs).isEqualToComparingFieldByField(testTaxClass);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxClass() throws Exception {
        int databaseSizeBeforeUpdate = taxClassRepository.findAll().size();

        // Create the TaxClass

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaxClassMockMvc.perform(put("/api/tax-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxClass)))
            .andExpect(status().isCreated());

        // Validate the TaxClass in the database
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaxClass() throws Exception {
        // Initialize the database
        taxClassService.save(taxClass);

        int databaseSizeBeforeDelete = taxClassRepository.findAll().size();

        // Get the taxClass
        restTaxClassMockMvc.perform(delete("/api/tax-classes/{id}", taxClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taxClassExistsInEs = taxClassSearchRepository.exists(taxClass.getId());
        assertThat(taxClassExistsInEs).isFalse();

        // Validate the database is empty
        List<TaxClass> taxClassList = taxClassRepository.findAll();
        assertThat(taxClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTaxClass() throws Exception {
        // Initialize the database
        taxClassService.save(taxClass);

        // Search the taxClass
        restTaxClassMockMvc.perform(get("/api/_search/tax-classes?query=id:" + taxClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxClass.class);
    }
}
