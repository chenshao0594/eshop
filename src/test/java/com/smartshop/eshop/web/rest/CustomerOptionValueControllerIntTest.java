package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOptionValue;
import com.smartshop.eshop.repository.CustomerOptionValueRepository;
import com.smartshop.eshop.service.CustomerOptionValueService;
import com.smartshop.eshop.repository.search.CustomerOptionValueSearchRepository;
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
 * Test class for the CustomerOptionValueResource REST controller.
 *
 * @see CustomerOptionValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptionValueControllerIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_OPTION_VALUE_IMAGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private CustomerOptionValueRepository customerOptionValueRepository;

    @Autowired
    private CustomerOptionValueService customerOptionValueService;

    @Autowired
    private CustomerOptionValueSearchRepository customerOptionValueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptionValueMockMvc;

    private CustomerOptionValue customerOptionValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptionValueController customerOptionValueController = new CustomerOptionValueController(customerOptionValueService);
        this.restCustomerOptionValueMockMvc = MockMvcBuilders.standaloneSetup(customerOptionValueController)
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
    public static CustomerOptionValue createEntity(EntityManager em) {
        CustomerOptionValue customerOptionValue = new CustomerOptionValue()
            .code(DEFAULT_CODE)
            .customerOptionValueImage(DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE)
            .sortOrder(DEFAULT_SORT_ORDER);
        return customerOptionValue;
    }

    @Before
    public void initTest() {
        customerOptionValueSearchRepository.deleteAll();
        customerOptionValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOptionValue() throws Exception {
        int databaseSizeBeforeCreate = customerOptionValueRepository.findAll().size();

        // Create the CustomerOptionValue
        restCustomerOptionValueMockMvc.perform(post("/api/customer-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValue)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionValue in the database
        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOptionValue testCustomerOptionValue = customerOptionValueList.get(customerOptionValueList.size() - 1);
        assertThat(testCustomerOptionValue.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerOptionValue.getCustomerOptionValueImage()).isEqualTo(DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE);
        assertThat(testCustomerOptionValue.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);

        // Validate the CustomerOptionValue in Elasticsearch
        CustomerOptionValue customerOptionValueEs = customerOptionValueSearchRepository.findOne(testCustomerOptionValue.getId());
        assertThat(customerOptionValueEs).isEqualToComparingFieldByField(testCustomerOptionValue);
    }

    @Test
    @Transactional
    public void createCustomerOptionValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptionValueRepository.findAll().size();

        // Create the CustomerOptionValue with an existing ID
        customerOptionValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptionValueMockMvc.perform(post("/api/customer-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValue)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOptionValueRepository.findAll().size();
        // set the field null
        customerOptionValue.setCode(null);

        // Create the CustomerOptionValue, which fails.

        restCustomerOptionValueMockMvc.perform(post("/api/customer-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValue)))
            .andExpect(status().isBadRequest());

        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOptionValues() throws Exception {
        // Initialize the database
        customerOptionValueRepository.saveAndFlush(customerOptionValue);

        // Get all the customerOptionValueList
        restCustomerOptionValueMockMvc.perform(get("/api/customer-option-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerOptionValueImage").value(hasItem(DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getCustomerOptionValue() throws Exception {
        // Initialize the database
        customerOptionValueRepository.saveAndFlush(customerOptionValue);

        // Get the customerOptionValue
        restCustomerOptionValueMockMvc.perform(get("/api/customer-option-values/{id}", customerOptionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOptionValue.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.customerOptionValueImage").value(DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOptionValue() throws Exception {
        // Get the customerOptionValue
        restCustomerOptionValueMockMvc.perform(get("/api/customer-option-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOptionValue() throws Exception {
        // Initialize the database
        customerOptionValueService.save(customerOptionValue);

        int databaseSizeBeforeUpdate = customerOptionValueRepository.findAll().size();

        // Update the customerOptionValue
        CustomerOptionValue updatedCustomerOptionValue = customerOptionValueRepository.findOne(customerOptionValue.getId());
        updatedCustomerOptionValue
            .code(UPDATED_CODE)
            .customerOptionValueImage(UPDATED_CUSTOMER_OPTION_VALUE_IMAGE)
            .sortOrder(UPDATED_SORT_ORDER);

        restCustomerOptionValueMockMvc.perform(put("/api/customer-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOptionValue)))
            .andExpect(status().isOk());

        // Validate the CustomerOptionValue in the database
        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeUpdate);
        CustomerOptionValue testCustomerOptionValue = customerOptionValueList.get(customerOptionValueList.size() - 1);
        assertThat(testCustomerOptionValue.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerOptionValue.getCustomerOptionValueImage()).isEqualTo(UPDATED_CUSTOMER_OPTION_VALUE_IMAGE);
        assertThat(testCustomerOptionValue.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);

        // Validate the CustomerOptionValue in Elasticsearch
        CustomerOptionValue customerOptionValueEs = customerOptionValueSearchRepository.findOne(testCustomerOptionValue.getId());
        assertThat(customerOptionValueEs).isEqualToComparingFieldByField(testCustomerOptionValue);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOptionValue() throws Exception {
        int databaseSizeBeforeUpdate = customerOptionValueRepository.findAll().size();

        // Create the CustomerOptionValue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptionValueMockMvc.perform(put("/api/customer-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionValue)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionValue in the database
        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOptionValue() throws Exception {
        // Initialize the database
        customerOptionValueService.save(customerOptionValue);

        int databaseSizeBeforeDelete = customerOptionValueRepository.findAll().size();

        // Get the customerOptionValue
        restCustomerOptionValueMockMvc.perform(delete("/api/customer-option-values/{id}", customerOptionValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptionValueExistsInEs = customerOptionValueSearchRepository.exists(customerOptionValue.getId());
        assertThat(customerOptionValueExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOptionValue> customerOptionValueList = customerOptionValueRepository.findAll();
        assertThat(customerOptionValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOptionValue() throws Exception {
        // Initialize the database
        customerOptionValueService.save(customerOptionValue);

        // Search the customerOptionValue
        restCustomerOptionValueMockMvc.perform(get("/api/_search/customer-option-values?query=id:" + customerOptionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerOptionValueImage").value(hasItem(DEFAULT_CUSTOMER_OPTION_VALUE_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOptionValue.class);
    }
}
