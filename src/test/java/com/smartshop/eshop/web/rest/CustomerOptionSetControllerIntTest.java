package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOptionSet;
import com.smartshop.eshop.repository.CustomerOptionSetRepository;
import com.smartshop.eshop.service.CustomerOptionSetService;
import com.smartshop.eshop.repository.search.CustomerOptionSetSearchRepository;
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
 * Test class for the CustomerOptionSetResource REST controller.
 *
 * @see CustomerOptionSetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptionSetControllerIntTest {

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private CustomerOptionSetRepository customerOptionSetRepository;

    @Autowired
    private CustomerOptionSetService customerOptionSetService;

    @Autowired
    private CustomerOptionSetSearchRepository customerOptionSetSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptionSetMockMvc;

    private CustomerOptionSet customerOptionSet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptionSetController customerOptionSetController = new CustomerOptionSetController(customerOptionSetService);
        this.restCustomerOptionSetMockMvc = MockMvcBuilders.standaloneSetup(customerOptionSetController)
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
    public static CustomerOptionSet createEntity(EntityManager em) {
        CustomerOptionSet customerOptionSet = new CustomerOptionSet()
            .sortOrder(DEFAULT_SORT_ORDER);
        return customerOptionSet;
    }

    @Before
    public void initTest() {
        customerOptionSetSearchRepository.deleteAll();
        customerOptionSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOptionSet() throws Exception {
        int databaseSizeBeforeCreate = customerOptionSetRepository.findAll().size();

        // Create the CustomerOptionSet
        restCustomerOptionSetMockMvc.perform(post("/api/customer-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionSet)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionSet in the database
        List<CustomerOptionSet> customerOptionSetList = customerOptionSetRepository.findAll();
        assertThat(customerOptionSetList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOptionSet testCustomerOptionSet = customerOptionSetList.get(customerOptionSetList.size() - 1);
        assertThat(testCustomerOptionSet.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);

        // Validate the CustomerOptionSet in Elasticsearch
        CustomerOptionSet customerOptionSetEs = customerOptionSetSearchRepository.findOne(testCustomerOptionSet.getId());
        assertThat(customerOptionSetEs).isEqualToComparingFieldByField(testCustomerOptionSet);
    }

    @Test
    @Transactional
    public void createCustomerOptionSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptionSetRepository.findAll().size();

        // Create the CustomerOptionSet with an existing ID
        customerOptionSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptionSetMockMvc.perform(post("/api/customer-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionSet)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOptionSet> customerOptionSetList = customerOptionSetRepository.findAll();
        assertThat(customerOptionSetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerOptionSets() throws Exception {
        // Initialize the database
        customerOptionSetRepository.saveAndFlush(customerOptionSet);

        // Get all the customerOptionSetList
        restCustomerOptionSetMockMvc.perform(get("/api/customer-option-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getCustomerOptionSet() throws Exception {
        // Initialize the database
        customerOptionSetRepository.saveAndFlush(customerOptionSet);

        // Get the customerOptionSet
        restCustomerOptionSetMockMvc.perform(get("/api/customer-option-sets/{id}", customerOptionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOptionSet.getId().intValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOptionSet() throws Exception {
        // Get the customerOptionSet
        restCustomerOptionSetMockMvc.perform(get("/api/customer-option-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOptionSet() throws Exception {
        // Initialize the database
        customerOptionSetService.save(customerOptionSet);

        int databaseSizeBeforeUpdate = customerOptionSetRepository.findAll().size();

        // Update the customerOptionSet
        CustomerOptionSet updatedCustomerOptionSet = customerOptionSetRepository.findOne(customerOptionSet.getId());
        updatedCustomerOptionSet
            .sortOrder(UPDATED_SORT_ORDER);

        restCustomerOptionSetMockMvc.perform(put("/api/customer-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOptionSet)))
            .andExpect(status().isOk());

        // Validate the CustomerOptionSet in the database
        List<CustomerOptionSet> customerOptionSetList = customerOptionSetRepository.findAll();
        assertThat(customerOptionSetList).hasSize(databaseSizeBeforeUpdate);
        CustomerOptionSet testCustomerOptionSet = customerOptionSetList.get(customerOptionSetList.size() - 1);
        assertThat(testCustomerOptionSet.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);

        // Validate the CustomerOptionSet in Elasticsearch
        CustomerOptionSet customerOptionSetEs = customerOptionSetSearchRepository.findOne(testCustomerOptionSet.getId());
        assertThat(customerOptionSetEs).isEqualToComparingFieldByField(testCustomerOptionSet);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOptionSet() throws Exception {
        int databaseSizeBeforeUpdate = customerOptionSetRepository.findAll().size();

        // Create the CustomerOptionSet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptionSetMockMvc.perform(put("/api/customer-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptionSet)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptionSet in the database
        List<CustomerOptionSet> customerOptionSetList = customerOptionSetRepository.findAll();
        assertThat(customerOptionSetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOptionSet() throws Exception {
        // Initialize the database
        customerOptionSetService.save(customerOptionSet);

        int databaseSizeBeforeDelete = customerOptionSetRepository.findAll().size();

        // Get the customerOptionSet
        restCustomerOptionSetMockMvc.perform(delete("/api/customer-option-sets/{id}", customerOptionSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptionSetExistsInEs = customerOptionSetSearchRepository.exists(customerOptionSet.getId());
        assertThat(customerOptionSetExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOptionSet> customerOptionSetList = customerOptionSetRepository.findAll();
        assertThat(customerOptionSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOptionSet() throws Exception {
        // Initialize the database
        customerOptionSetService.save(customerOptionSet);

        // Search the customerOptionSet
        restCustomerOptionSetMockMvc.perform(get("/api/_search/customer-option-sets?query=id:" + customerOptionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOptionSet.class);
    }
}
