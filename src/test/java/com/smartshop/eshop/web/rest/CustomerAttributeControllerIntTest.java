package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerAttribute;
import com.smartshop.eshop.repository.CustomerAttributeRepository;
import com.smartshop.eshop.service.CustomerAttributeService;
import com.smartshop.eshop.repository.search.CustomerAttributeSearchRepository;
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
 * Test class for the CustomerAttributeResource REST controller.
 *
 * @see CustomerAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerAttributeControllerIntTest {

    private static final String DEFAULT_TEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_VALUE = "BBBBBBBBBB";

    @Autowired
    private CustomerAttributeRepository customerAttributeRepository;

    @Autowired
    private CustomerAttributeService customerAttributeService;

    @Autowired
    private CustomerAttributeSearchRepository customerAttributeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerAttributeMockMvc;

    private CustomerAttribute customerAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerAttributeController customerAttributeController = new CustomerAttributeController(customerAttributeService);
        this.restCustomerAttributeMockMvc = MockMvcBuilders.standaloneSetup(customerAttributeController)
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
    public static CustomerAttribute createEntity(EntityManager em) {
        CustomerAttribute customerAttribute = new CustomerAttribute()
            .textValue(DEFAULT_TEXT_VALUE);
        return customerAttribute;
    }

    @Before
    public void initTest() {
        customerAttributeSearchRepository.deleteAll();
        customerAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAttribute() throws Exception {
        int databaseSizeBeforeCreate = customerAttributeRepository.findAll().size();

        // Create the CustomerAttribute
        restCustomerAttributeMockMvc.perform(post("/api/customer-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAttribute)))
            .andExpect(status().isCreated());

        // Validate the CustomerAttribute in the database
        List<CustomerAttribute> customerAttributeList = customerAttributeRepository.findAll();
        assertThat(customerAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAttribute testCustomerAttribute = customerAttributeList.get(customerAttributeList.size() - 1);
        assertThat(testCustomerAttribute.getTextValue()).isEqualTo(DEFAULT_TEXT_VALUE);

        // Validate the CustomerAttribute in Elasticsearch
        CustomerAttribute customerAttributeEs = customerAttributeSearchRepository.findOne(testCustomerAttribute.getId());
        assertThat(customerAttributeEs).isEqualToComparingFieldByField(testCustomerAttribute);
    }

    @Test
    @Transactional
    public void createCustomerAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAttributeRepository.findAll().size();

        // Create the CustomerAttribute with an existing ID
        customerAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAttributeMockMvc.perform(post("/api/customer-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerAttribute> customerAttributeList = customerAttributeRepository.findAll();
        assertThat(customerAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerAttributes() throws Exception {
        // Initialize the database
        customerAttributeRepository.saveAndFlush(customerAttribute);

        // Get all the customerAttributeList
        restCustomerAttributeMockMvc.perform(get("/api/customer-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].textValue").value(hasItem(DEFAULT_TEXT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getCustomerAttribute() throws Exception {
        // Initialize the database
        customerAttributeRepository.saveAndFlush(customerAttribute);

        // Get the customerAttribute
        restCustomerAttributeMockMvc.perform(get("/api/customer-attributes/{id}", customerAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerAttribute.getId().intValue()))
            .andExpect(jsonPath("$.textValue").value(DEFAULT_TEXT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerAttribute() throws Exception {
        // Get the customerAttribute
        restCustomerAttributeMockMvc.perform(get("/api/customer-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAttribute() throws Exception {
        // Initialize the database
        customerAttributeService.save(customerAttribute);

        int databaseSizeBeforeUpdate = customerAttributeRepository.findAll().size();

        // Update the customerAttribute
        CustomerAttribute updatedCustomerAttribute = customerAttributeRepository.findOne(customerAttribute.getId());
        updatedCustomerAttribute
            .textValue(UPDATED_TEXT_VALUE);

        restCustomerAttributeMockMvc.perform(put("/api/customer-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerAttribute)))
            .andExpect(status().isOk());

        // Validate the CustomerAttribute in the database
        List<CustomerAttribute> customerAttributeList = customerAttributeRepository.findAll();
        assertThat(customerAttributeList).hasSize(databaseSizeBeforeUpdate);
        CustomerAttribute testCustomerAttribute = customerAttributeList.get(customerAttributeList.size() - 1);
        assertThat(testCustomerAttribute.getTextValue()).isEqualTo(UPDATED_TEXT_VALUE);

        // Validate the CustomerAttribute in Elasticsearch
        CustomerAttribute customerAttributeEs = customerAttributeSearchRepository.findOne(testCustomerAttribute.getId());
        assertThat(customerAttributeEs).isEqualToComparingFieldByField(testCustomerAttribute);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAttribute() throws Exception {
        int databaseSizeBeforeUpdate = customerAttributeRepository.findAll().size();

        // Create the CustomerAttribute

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerAttributeMockMvc.perform(put("/api/customer-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAttribute)))
            .andExpect(status().isCreated());

        // Validate the CustomerAttribute in the database
        List<CustomerAttribute> customerAttributeList = customerAttributeRepository.findAll();
        assertThat(customerAttributeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerAttribute() throws Exception {
        // Initialize the database
        customerAttributeService.save(customerAttribute);

        int databaseSizeBeforeDelete = customerAttributeRepository.findAll().size();

        // Get the customerAttribute
        restCustomerAttributeMockMvc.perform(delete("/api/customer-attributes/{id}", customerAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerAttributeExistsInEs = customerAttributeSearchRepository.exists(customerAttribute.getId());
        assertThat(customerAttributeExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerAttribute> customerAttributeList = customerAttributeRepository.findAll();
        assertThat(customerAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerAttribute() throws Exception {
        // Initialize the database
        customerAttributeService.save(customerAttribute);

        // Search the customerAttribute
        restCustomerAttributeMockMvc.perform(get("/api/_search/customer-attributes?query=id:" + customerAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].textValue").value(hasItem(DEFAULT_TEXT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAttribute.class);
    }
}
