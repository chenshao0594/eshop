package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.TaxRateDescription;
import com.smartshop.eshop.repository.TaxRateDescriptionRepository;
import com.smartshop.eshop.service.TaxRateDescriptionService;
import com.smartshop.eshop.repository.search.TaxRateDescriptionSearchRepository;
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
 * Test class for the TaxRateDescriptionResource REST controller.
 *
 * @see TaxRateDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class TaxRateDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TaxRateDescriptionRepository taxRateDescriptionRepository;

    @Autowired
    private TaxRateDescriptionService taxRateDescriptionService;

    @Autowired
    private TaxRateDescriptionSearchRepository taxRateDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaxRateDescriptionMockMvc;

    private TaxRateDescription taxRateDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaxRateDescriptionController taxRateDescriptionController = new TaxRateDescriptionController(taxRateDescriptionService);
        this.restTaxRateDescriptionMockMvc = MockMvcBuilders.standaloneSetup(taxRateDescriptionController)
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
    public static TaxRateDescription createEntity(EntityManager em) {
        TaxRateDescription taxRateDescription = new TaxRateDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return taxRateDescription;
    }

    @Before
    public void initTest() {
        taxRateDescriptionSearchRepository.deleteAll();
        taxRateDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxRateDescription() throws Exception {
        int databaseSizeBeforeCreate = taxRateDescriptionRepository.findAll().size();

        // Create the TaxRateDescription
        restTaxRateDescriptionMockMvc.perform(post("/api/tax-rate-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRateDescription)))
            .andExpect(status().isCreated());

        // Validate the TaxRateDescription in the database
        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        TaxRateDescription testTaxRateDescription = taxRateDescriptionList.get(taxRateDescriptionList.size() - 1);
        assertThat(testTaxRateDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTaxRateDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxRateDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the TaxRateDescription in Elasticsearch
        TaxRateDescription taxRateDescriptionEs = taxRateDescriptionSearchRepository.findOne(testTaxRateDescription.getId());
        assertThat(taxRateDescriptionEs).isEqualToComparingFieldByField(testTaxRateDescription);
    }

    @Test
    @Transactional
    public void createTaxRateDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxRateDescriptionRepository.findAll().size();

        // Create the TaxRateDescription with an existing ID
        taxRateDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxRateDescriptionMockMvc.perform(post("/api/tax-rate-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRateDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxRateDescriptionRepository.findAll().size();
        // set the field null
        taxRateDescription.setName(null);

        // Create the TaxRateDescription, which fails.

        restTaxRateDescriptionMockMvc.perform(post("/api/tax-rate-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRateDescription)))
            .andExpect(status().isBadRequest());

        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxRateDescriptions() throws Exception {
        // Initialize the database
        taxRateDescriptionRepository.saveAndFlush(taxRateDescription);

        // Get all the taxRateDescriptionList
        restTaxRateDescriptionMockMvc.perform(get("/api/tax-rate-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxRateDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTaxRateDescription() throws Exception {
        // Initialize the database
        taxRateDescriptionRepository.saveAndFlush(taxRateDescription);

        // Get the taxRateDescription
        restTaxRateDescriptionMockMvc.perform(get("/api/tax-rate-descriptions/{id}", taxRateDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(taxRateDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxRateDescription() throws Exception {
        // Get the taxRateDescription
        restTaxRateDescriptionMockMvc.perform(get("/api/tax-rate-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxRateDescription() throws Exception {
        // Initialize the database
        taxRateDescriptionService.save(taxRateDescription);

        int databaseSizeBeforeUpdate = taxRateDescriptionRepository.findAll().size();

        // Update the taxRateDescription
        TaxRateDescription updatedTaxRateDescription = taxRateDescriptionRepository.findOne(taxRateDescription.getId());
        updatedTaxRateDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTaxRateDescriptionMockMvc.perform(put("/api/tax-rate-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxRateDescription)))
            .andExpect(status().isOk());

        // Validate the TaxRateDescription in the database
        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeUpdate);
        TaxRateDescription testTaxRateDescription = taxRateDescriptionList.get(taxRateDescriptionList.size() - 1);
        assertThat(testTaxRateDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTaxRateDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxRateDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the TaxRateDescription in Elasticsearch
        TaxRateDescription taxRateDescriptionEs = taxRateDescriptionSearchRepository.findOne(testTaxRateDescription.getId());
        assertThat(taxRateDescriptionEs).isEqualToComparingFieldByField(testTaxRateDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxRateDescription() throws Exception {
        int databaseSizeBeforeUpdate = taxRateDescriptionRepository.findAll().size();

        // Create the TaxRateDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaxRateDescriptionMockMvc.perform(put("/api/tax-rate-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(taxRateDescription)))
            .andExpect(status().isCreated());

        // Validate the TaxRateDescription in the database
        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTaxRateDescription() throws Exception {
        // Initialize the database
        taxRateDescriptionService.save(taxRateDescription);

        int databaseSizeBeforeDelete = taxRateDescriptionRepository.findAll().size();

        // Get the taxRateDescription
        restTaxRateDescriptionMockMvc.perform(delete("/api/tax-rate-descriptions/{id}", taxRateDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taxRateDescriptionExistsInEs = taxRateDescriptionSearchRepository.exists(taxRateDescription.getId());
        assertThat(taxRateDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<TaxRateDescription> taxRateDescriptionList = taxRateDescriptionRepository.findAll();
        assertThat(taxRateDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTaxRateDescription() throws Exception {
        // Initialize the database
        taxRateDescriptionService.save(taxRateDescription);

        // Search the taxRateDescription
        restTaxRateDescriptionMockMvc.perform(get("/api/_search/tax-rate-descriptions?query=id:" + taxRateDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxRateDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaxRateDescription.class);
    }
}
