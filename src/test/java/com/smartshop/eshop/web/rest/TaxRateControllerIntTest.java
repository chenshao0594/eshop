package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.TaxRate;
import com.smartshop.eshop.repository.TaxRateRepository;
import com.smartshop.eshop.service.TaxRateService;
import com.smartshop.eshop.repository.search.TaxRateSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaxRateResource REST controller.
 *
 * @see TaxRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class TaxRateControllerIntTest {

    private static final Boolean DEFAULT_PIGGYBACK = false;
    private static final Boolean UPDATED_PIGGYBACK = true;

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAX_PRIORITY = 1;
    private static final Integer UPDATED_TAX_PRIORITY = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_RATE = new BigDecimal(2);

    @Autowired
    private TaxRateRepository taxRateRepository;

    @Autowired
    private TaxRateService taxRateService;

    @Autowired
    private TaxRateSearchRepository taxRateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaxRateMockMvc;

    private TaxRate taxRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxRateController taxRateController = new TaxRateController(taxRateService);
        this.restTaxRateMockMvc = MockMvcBuilders.standaloneSetup(taxRateController)
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
    public static TaxRate createEntity(EntityManager em) {
        TaxRate taxRate = new TaxRate()
            .piggyback(DEFAULT_PIGGYBACK)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .taxPriority(DEFAULT_TAX_PRIORITY)
            .code(DEFAULT_CODE)
            .taxRate(DEFAULT_TAX_RATE);
        return taxRate;
    }

    @Before
    public void initTest() {
        taxRateSearchRepository.deleteAll();
        taxRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxRate() throws Exception {
        int databaseSizeBeforeCreate = taxRateRepository.findAll().size();

        // Create the TaxRate
        restTaxRateMockMvc.perform(post("/api/tax-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRate)))
            .andExpect(status().isCreated());

        // Validate the TaxRate in the database
        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeCreate + 1);
        TaxRate testTaxRate = taxRateList.get(taxRateList.size() - 1);
        assertThat(testTaxRate.isPiggyback()).isEqualTo(DEFAULT_PIGGYBACK);
        assertThat(testTaxRate.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testTaxRate.getTaxPriority()).isEqualTo(DEFAULT_TAX_PRIORITY);
        assertThat(testTaxRate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTaxRate.getTaxRate()).isEqualTo(DEFAULT_TAX_RATE);

        // Validate the TaxRate in Elasticsearch
        TaxRate taxRateEs = taxRateSearchRepository.findOne(testTaxRate.getId());
        assertThat(taxRateEs).isEqualToComparingFieldByField(testTaxRate);
    }

    @Test
    @Transactional
    public void createTaxRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxRateRepository.findAll().size();

        // Create the TaxRate with an existing ID
        taxRate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxRateMockMvc.perform(post("/api/tax-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRateRepository.findAll().size();
        // set the field null
        taxRate.setCode(null);

        // Create the TaxRate, which fails.

        restTaxRateMockMvc.perform(post("/api/tax-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRate)))
            .andExpect(status().isBadRequest());

        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxRates() throws Exception {
        // Initialize the database
        taxRateRepository.saveAndFlush(taxRate);

        // Get all the taxRateList
        restTaxRateMockMvc.perform(get("/api/tax-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].piggyback").value(hasItem(DEFAULT_PIGGYBACK.booleanValue())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].taxPriority").value(hasItem(DEFAULT_TAX_PRIORITY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())));
    }

    @Test
    @Transactional
    public void getTaxRate() throws Exception {
        // Initialize the database
        taxRateRepository.saveAndFlush(taxRate);

        // Get the taxRate
        restTaxRateMockMvc.perform(get("/api/tax-rates/{id}", taxRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxRate.getId().intValue()))
            .andExpect(jsonPath("$.piggyback").value(DEFAULT_PIGGYBACK.booleanValue()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.taxPriority").value(DEFAULT_TAX_PRIORITY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.taxRate").value(DEFAULT_TAX_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxRate() throws Exception {
        // Get the taxRate
        restTaxRateMockMvc.perform(get("/api/tax-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxRate() throws Exception {
        // Initialize the database
        taxRateService.save(taxRate);

        int databaseSizeBeforeUpdate = taxRateRepository.findAll().size();

        // Update the taxRate
        TaxRate updatedTaxRate = taxRateRepository.findOne(taxRate.getId());
        updatedTaxRate
            .piggyback(UPDATED_PIGGYBACK)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .taxPriority(UPDATED_TAX_PRIORITY)
            .code(UPDATED_CODE)
            .taxRate(UPDATED_TAX_RATE);

        restTaxRateMockMvc.perform(put("/api/tax-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxRate)))
            .andExpect(status().isOk());

        // Validate the TaxRate in the database
        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeUpdate);
        TaxRate testTaxRate = taxRateList.get(taxRateList.size() - 1);
        assertThat(testTaxRate.isPiggyback()).isEqualTo(UPDATED_PIGGYBACK);
        assertThat(testTaxRate.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testTaxRate.getTaxPriority()).isEqualTo(UPDATED_TAX_PRIORITY);
        assertThat(testTaxRate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTaxRate.getTaxRate()).isEqualTo(UPDATED_TAX_RATE);

        // Validate the TaxRate in Elasticsearch
        TaxRate taxRateEs = taxRateSearchRepository.findOne(testTaxRate.getId());
        assertThat(taxRateEs).isEqualToComparingFieldByField(testTaxRate);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxRate() throws Exception {
        int databaseSizeBeforeUpdate = taxRateRepository.findAll().size();

        // Create the TaxRate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaxRateMockMvc.perform(put("/api/tax-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRate)))
            .andExpect(status().isCreated());

        // Validate the TaxRate in the database
        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaxRate() throws Exception {
        // Initialize the database
        taxRateService.save(taxRate);

        int databaseSizeBeforeDelete = taxRateRepository.findAll().size();

        // Get the taxRate
        restTaxRateMockMvc.perform(delete("/api/tax-rates/{id}", taxRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taxRateExistsInEs = taxRateSearchRepository.exists(taxRate.getId());
        assertThat(taxRateExistsInEs).isFalse();

        // Validate the database is empty
        List<TaxRate> taxRateList = taxRateRepository.findAll();
        assertThat(taxRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTaxRate() throws Exception {
        // Initialize the database
        taxRateService.save(taxRate);

        // Search the taxRate
        restTaxRateMockMvc.perform(get("/api/_search/tax-rates?query=id:" + taxRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].piggyback").value(hasItem(DEFAULT_PIGGYBACK.booleanValue())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].taxPriority").value(hasItem(DEFAULT_TAX_PRIORITY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].taxRate").value(hasItem(DEFAULT_TAX_RATE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxRate.class);
    }
}
