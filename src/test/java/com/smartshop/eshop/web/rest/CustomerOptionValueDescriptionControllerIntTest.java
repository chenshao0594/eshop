package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;
import com.smartshop.eshop.repository.CustomerOptionValueDescriptionRepository;
import com.smartshop.eshop.service.CustomerOptionValueDescriptionService;
import com.smartshop.eshop.repository.search.CustomerOptionValueDescriptionSearchRepository;
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
 * Test class for the CustomerOptionValueDescriptionResource REST controller.
 *
 * @see CustomerOptionValueDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptionValueDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CustomerOptionValueDescriptionRepository customerOptionValueDescriptionRepository;

    @Autowired
    private CustomerOptionValueDescriptionService customerOptionValueDescriptionService;

    @Autowired
    private CustomerOptionValueDescriptionSearchRepository customerOptionValueDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptionValueDescriptionMockMvc;

    private CustomerOptionValueDescription customerOptionValueDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptionValueDescriptionController customerOptionValueDescriptionController = new CustomerOptionValueDescriptionController(customerOptionValueDescriptionService);
        this.restCustomerOptionValueDescriptionMockMvc = MockMvcBuilders.standaloneSetup(customerOptionValueDescriptionController)
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
    public static CustomerOptionValueDescription createEntity(EntityManager em) {
        CustomerOptionValueDescription customerOptionValueDescription = new CustomerOptionValueDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return customerOptionValueDescription;
    }

    @Before
    public void initTest() {
        customerOptionValueDescriptionSearchRepository.deleteAll();
        customerOptionValueDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOptionValueDescription() throws Exception {
        int databaseSizeBeforeCreate = customerOptionValueDescriptionRepository.findAll().size();

        // Create the CustomerOptionValueDescription
        restCustomerOptionValueDescriptionMockMvc.perform(post("/api/customer-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValueDescription)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionValueDescription in the database
        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOptionValueDescription testCustomerOptionValueDescription = customerOptionValueDescriptionList.get(customerOptionValueDescriptionList.size() - 1);
        assertThat(testCustomerOptionValueDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomerOptionValueDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerOptionValueDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CustomerOptionValueDescription in Elasticsearch
        CustomerOptionValueDescription customerOptionValueDescriptionEs = customerOptionValueDescriptionSearchRepository.findOne(testCustomerOptionValueDescription.getId());
        assertThat(customerOptionValueDescriptionEs).isEqualToComparingFieldByField(testCustomerOptionValueDescription);
    }

    @Test
    @Transactional
    public void createCustomerOptionValueDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptionValueDescriptionRepository.findAll().size();

        // Create the CustomerOptionValueDescription with an existing ID
        customerOptionValueDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptionValueDescriptionMockMvc.perform(post("/api/customer-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValueDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOptionValueDescriptionRepository.findAll().size();
        // set the field null
        customerOptionValueDescription.setName(null);

        // Create the CustomerOptionValueDescription, which fails.

        restCustomerOptionValueDescriptionMockMvc.perform(post("/api/customer-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValueDescription)))
            .andExpect(status().isBadRequest());

        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOptionValueDescriptions() throws Exception {
        // Initialize the database
        customerOptionValueDescriptionRepository.saveAndFlush(customerOptionValueDescription);

        // Get all the customerOptionValueDescriptionList
        restCustomerOptionValueDescriptionMockMvc.perform(get("/api/customer-option-value-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionValueDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCustomerOptionValueDescription() throws Exception {
        // Initialize the database
        customerOptionValueDescriptionRepository.saveAndFlush(customerOptionValueDescription);

        // Get the customerOptionValueDescription
        restCustomerOptionValueDescriptionMockMvc.perform(get("/api/customer-option-value-descriptions/{id}", customerOptionValueDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOptionValueDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOptionValueDescription() throws Exception {
        // Get the customerOptionValueDescription
        restCustomerOptionValueDescriptionMockMvc.perform(get("/api/customer-option-value-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOptionValueDescription() throws Exception {
        // Initialize the database
        customerOptionValueDescriptionService.save(customerOptionValueDescription);

        int databaseSizeBeforeUpdate = customerOptionValueDescriptionRepository.findAll().size();

        // Update the customerOptionValueDescription
        CustomerOptionValueDescription updatedCustomerOptionValueDescription = customerOptionValueDescriptionRepository.findOne(customerOptionValueDescription.getId());
        updatedCustomerOptionValueDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCustomerOptionValueDescriptionMockMvc.perform(put("/api/customer-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOptionValueDescription)))
            .andExpect(status().isOk());

        // Validate the CustomerOptionValueDescription in the database
        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeUpdate);
        CustomerOptionValueDescription testCustomerOptionValueDescription = customerOptionValueDescriptionList.get(customerOptionValueDescriptionList.size() - 1);
        assertThat(testCustomerOptionValueDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomerOptionValueDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerOptionValueDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CustomerOptionValueDescription in Elasticsearch
        CustomerOptionValueDescription customerOptionValueDescriptionEs = customerOptionValueDescriptionSearchRepository.findOne(testCustomerOptionValueDescription.getId());
        assertThat(customerOptionValueDescriptionEs).isEqualToComparingFieldByField(testCustomerOptionValueDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOptionValueDescription() throws Exception {
        int databaseSizeBeforeUpdate = customerOptionValueDescriptionRepository.findAll().size();

        // Create the CustomerOptionValueDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptionValueDescriptionMockMvc.perform(put("/api/customer-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValueDescription)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionValueDescription in the database
        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOptionValueDescription() throws Exception {
        // Initialize the database
        customerOptionValueDescriptionService.save(customerOptionValueDescription);

        int databaseSizeBeforeDelete = customerOptionValueDescriptionRepository.findAll().size();

        // Get the customerOptionValueDescription
        restCustomerOptionValueDescriptionMockMvc.perform(delete("/api/customer-option-value-descriptions/{id}", customerOptionValueDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptionValueDescriptionExistsInEs = customerOptionValueDescriptionSearchRepository.exists(customerOptionValueDescription.getId());
        assertThat(customerOptionValueDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOptionValueDescription> customerOptionValueDescriptionList = customerOptionValueDescriptionRepository.findAll();
        assertThat(customerOptionValueDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOptionValueDescription() throws Exception {
        // Initialize the database
        customerOptionValueDescriptionService.save(customerOptionValueDescription);

        // Search the customerOptionValueDescription
        restCustomerOptionValueDescriptionMockMvc.perform(get("/api/_search/customer-option-value-descriptions?query=id:" + customerOptionValueDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionValueDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOptionValueDescription.class);
    }
}
