package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOptionDescription;
import com.smartshop.eshop.repository.CustomerOptionDescriptionRepository;
import com.smartshop.eshop.service.CustomerOptionDescriptionService;
import com.smartshop.eshop.repository.search.CustomerOptionDescriptionSearchRepository;
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
 * Test class for the CustomerOptionDescriptionResource REST controller.
 *
 * @see CustomerOptionDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptionDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_OPTION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_OPTION_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CustomerOptionDescriptionRepository customerOptionDescriptionRepository;

    @Autowired
    private CustomerOptionDescriptionService customerOptionDescriptionService;

    @Autowired
    private CustomerOptionDescriptionSearchRepository customerOptionDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptionDescriptionMockMvc;

    private CustomerOptionDescription customerOptionDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptionDescriptionController customerOptionDescriptionController = new CustomerOptionDescriptionController(customerOptionDescriptionService);
        this.restCustomerOptionDescriptionMockMvc = MockMvcBuilders.standaloneSetup(customerOptionDescriptionController)
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
    public static CustomerOptionDescription createEntity(EntityManager em) {
        CustomerOptionDescription customerOptionDescription = new CustomerOptionDescription()
            .title(DEFAULT_TITLE)
            .customerOptionComment(DEFAULT_CUSTOMER_OPTION_COMMENT)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return customerOptionDescription;
    }

    @Before
    public void initTest() {
        customerOptionDescriptionSearchRepository.deleteAll();
        customerOptionDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOptionDescription() throws Exception {
        int databaseSizeBeforeCreate = customerOptionDescriptionRepository.findAll().size();

        // Create the CustomerOptionDescription
        restCustomerOptionDescriptionMockMvc.perform(post("/api/customer-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionDescription)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionDescription in the database
        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOptionDescription testCustomerOptionDescription = customerOptionDescriptionList.get(customerOptionDescriptionList.size() - 1);
        assertThat(testCustomerOptionDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomerOptionDescription.getCustomerOptionComment()).isEqualTo(DEFAULT_CUSTOMER_OPTION_COMMENT);
        assertThat(testCustomerOptionDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerOptionDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CustomerOptionDescription in Elasticsearch
        CustomerOptionDescription customerOptionDescriptionEs = customerOptionDescriptionSearchRepository.findOne(testCustomerOptionDescription.getId());
        assertThat(customerOptionDescriptionEs).isEqualToComparingFieldByField(testCustomerOptionDescription);
    }

    @Test
    @Transactional
    public void createCustomerOptionDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptionDescriptionRepository.findAll().size();

        // Create the CustomerOptionDescription with an existing ID
        customerOptionDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptionDescriptionMockMvc.perform(post("/api/customer-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOptionDescriptionRepository.findAll().size();
        // set the field null
        customerOptionDescription.setName(null);

        // Create the CustomerOptionDescription, which fails.

        restCustomerOptionDescriptionMockMvc.perform(post("/api/customer-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionDescription)))
            .andExpect(status().isBadRequest());

        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOptionDescriptions() throws Exception {
        // Initialize the database
        customerOptionDescriptionRepository.saveAndFlush(customerOptionDescription);

        // Get all the customerOptionDescriptionList
        restCustomerOptionDescriptionMockMvc.perform(get("/api/customer-option-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].customerOptionComment").value(hasItem(DEFAULT_CUSTOMER_OPTION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCustomerOptionDescription() throws Exception {
        // Initialize the database
        customerOptionDescriptionRepository.saveAndFlush(customerOptionDescription);

        // Get the customerOptionDescription
        restCustomerOptionDescriptionMockMvc.perform(get("/api/customer-option-descriptions/{id}", customerOptionDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOptionDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.customerOptionComment").value(DEFAULT_CUSTOMER_OPTION_COMMENT.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOptionDescription() throws Exception {
        // Get the customerOptionDescription
        restCustomerOptionDescriptionMockMvc.perform(get("/api/customer-option-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOptionDescription() throws Exception {
        // Initialize the database
        customerOptionDescriptionService.save(customerOptionDescription);

        int databaseSizeBeforeUpdate = customerOptionDescriptionRepository.findAll().size();

        // Update the customerOptionDescription
        CustomerOptionDescription updatedCustomerOptionDescription = customerOptionDescriptionRepository.findOne(customerOptionDescription.getId());
        updatedCustomerOptionDescription
            .title(UPDATED_TITLE)
            .customerOptionComment(UPDATED_CUSTOMER_OPTION_COMMENT)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCustomerOptionDescriptionMockMvc.perform(put("/api/customer-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOptionDescription)))
            .andExpect(status().isOk());

        // Validate the CustomerOptionDescription in the database
        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeUpdate);
        CustomerOptionDescription testCustomerOptionDescription = customerOptionDescriptionList.get(customerOptionDescriptionList.size() - 1);
        assertThat(testCustomerOptionDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomerOptionDescription.getCustomerOptionComment()).isEqualTo(UPDATED_CUSTOMER_OPTION_COMMENT);
        assertThat(testCustomerOptionDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerOptionDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CustomerOptionDescription in Elasticsearch
        CustomerOptionDescription customerOptionDescriptionEs = customerOptionDescriptionSearchRepository.findOne(testCustomerOptionDescription.getId());
        assertThat(customerOptionDescriptionEs).isEqualToComparingFieldByField(testCustomerOptionDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOptionDescription() throws Exception {
        int databaseSizeBeforeUpdate = customerOptionDescriptionRepository.findAll().size();

        // Create the CustomerOptionDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptionDescriptionMockMvc.perform(put("/api/customer-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionDescription)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionDescription in the database
        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOptionDescription() throws Exception {
        // Initialize the database
        customerOptionDescriptionService.save(customerOptionDescription);

        int databaseSizeBeforeDelete = customerOptionDescriptionRepository.findAll().size();

        // Get the customerOptionDescription
        restCustomerOptionDescriptionMockMvc.perform(delete("/api/customer-option-descriptions/{id}", customerOptionDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptionDescriptionExistsInEs = customerOptionDescriptionSearchRepository.exists(customerOptionDescription.getId());
        assertThat(customerOptionDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOptionDescription> customerOptionDescriptionList = customerOptionDescriptionRepository.findAll();
        assertThat(customerOptionDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOptionDescription() throws Exception {
        // Initialize the database
        customerOptionDescriptionService.save(customerOptionDescription);

        // Search the customerOptionDescription
        restCustomerOptionDescriptionMockMvc.perform(get("/api/_search/customer-option-descriptions?query=id:" + customerOptionDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].customerOptionComment").value(hasItem(DEFAULT_CUSTOMER_OPTION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOptionDescription.class);
    }
}
