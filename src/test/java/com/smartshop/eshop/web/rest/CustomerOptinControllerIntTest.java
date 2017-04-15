package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOptin;
import com.smartshop.eshop.repository.CustomerOptinRepository;
import com.smartshop.eshop.service.CustomerOptinService;
import com.smartshop.eshop.repository.search.CustomerOptinSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerOptinResource REST controller.
 *
 * @see CustomerOptinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptinControllerIntTest {

    private static final LocalDate DEFAULT_OPTIN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPTIN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private CustomerOptinRepository customerOptinRepository;

    @Autowired
    private CustomerOptinService customerOptinService;

    @Autowired
    private CustomerOptinSearchRepository customerOptinSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptinMockMvc;

    private CustomerOptin customerOptin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptinController customerOptinController = new CustomerOptinController(customerOptinService);
        this.restCustomerOptinMockMvc = MockMvcBuilders.standaloneSetup(customerOptinController)
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
    public static CustomerOptin createEntity(EntityManager em) {
        CustomerOptin customerOptin = new CustomerOptin()
            .optinDate(DEFAULT_OPTIN_DATE)
            .email(DEFAULT_EMAIL)
            .value(DEFAULT_VALUE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return customerOptin;
    }

    @Before
    public void initTest() {
        customerOptinSearchRepository.deleteAll();
        customerOptin = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOptin() throws Exception {
        int databaseSizeBeforeCreate = customerOptinRepository.findAll().size();

        // Create the CustomerOptin
        restCustomerOptinMockMvc.perform(post("/api/customer-optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptin)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptin in the database
        List<CustomerOptin> customerOptinList = customerOptinRepository.findAll();
        assertThat(customerOptinList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOptin testCustomerOptin = customerOptinList.get(customerOptinList.size() - 1);
        assertThat(testCustomerOptin.getOptinDate()).isEqualTo(DEFAULT_OPTIN_DATE);
        assertThat(testCustomerOptin.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomerOptin.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustomerOptin.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomerOptin.getLastName()).isEqualTo(DEFAULT_LAST_NAME);

        // Validate the CustomerOptin in Elasticsearch
        CustomerOptin customerOptinEs = customerOptinSearchRepository.findOne(testCustomerOptin.getId());
        assertThat(customerOptinEs).isEqualToComparingFieldByField(testCustomerOptin);
    }

    @Test
    @Transactional
    public void createCustomerOptinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptinRepository.findAll().size();

        // Create the CustomerOptin with an existing ID
        customerOptin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptinMockMvc.perform(post("/api/customer-optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOptin> customerOptinList = customerOptinRepository.findAll();
        assertThat(customerOptinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCustomerOptins() throws Exception {
        // Initialize the database
        customerOptinRepository.saveAndFlush(customerOptin);

        // Get all the customerOptinList
        restCustomerOptinMockMvc.perform(get("/api/customer-optins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptin.getId().intValue())))
            .andExpect(jsonPath("$.[*].optinDate").value(hasItem(DEFAULT_OPTIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCustomerOptin() throws Exception {
        // Initialize the database
        customerOptinRepository.saveAndFlush(customerOptin);

        // Get the customerOptin
        restCustomerOptinMockMvc.perform(get("/api/customer-optins/{id}", customerOptin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOptin.getId().intValue()))
            .andExpect(jsonPath("$.optinDate").value(DEFAULT_OPTIN_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOptin() throws Exception {
        // Get the customerOptin
        restCustomerOptinMockMvc.perform(get("/api/customer-optins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOptin() throws Exception {
        // Initialize the database
        customerOptinService.save(customerOptin);

        int databaseSizeBeforeUpdate = customerOptinRepository.findAll().size();

        // Update the customerOptin
        CustomerOptin updatedCustomerOptin = customerOptinRepository.findOne(customerOptin.getId());
        updatedCustomerOptin
            .optinDate(UPDATED_OPTIN_DATE)
            .email(UPDATED_EMAIL)
            .value(UPDATED_VALUE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);

        restCustomerOptinMockMvc.perform(put("/api/customer-optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOptin)))
            .andExpect(status().isOk());

        // Validate the CustomerOptin in the database
        List<CustomerOptin> customerOptinList = customerOptinRepository.findAll();
        assertThat(customerOptinList).hasSize(databaseSizeBeforeUpdate);
        CustomerOptin testCustomerOptin = customerOptinList.get(customerOptinList.size() - 1);
        assertThat(testCustomerOptin.getOptinDate()).isEqualTo(UPDATED_OPTIN_DATE);
        assertThat(testCustomerOptin.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomerOptin.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustomerOptin.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomerOptin.getLastName()).isEqualTo(UPDATED_LAST_NAME);

        // Validate the CustomerOptin in Elasticsearch
        CustomerOptin customerOptinEs = customerOptinSearchRepository.findOne(testCustomerOptin.getId());
        assertThat(customerOptinEs).isEqualToComparingFieldByField(testCustomerOptin);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOptin() throws Exception {
        int databaseSizeBeforeUpdate = customerOptinRepository.findAll().size();

        // Create the CustomerOptin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptinMockMvc.perform(put("/api/customer-optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOptin)))
            .andExpect(status().isCreated());

        // Validate the CustomerOptin in the database
        List<CustomerOptin> customerOptinList = customerOptinRepository.findAll();
        assertThat(customerOptinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOptin() throws Exception {
        // Initialize the database
        customerOptinService.save(customerOptin);

        int databaseSizeBeforeDelete = customerOptinRepository.findAll().size();

        // Get the customerOptin
        restCustomerOptinMockMvc.perform(delete("/api/customer-optins/{id}", customerOptin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptinExistsInEs = customerOptinSearchRepository.exists(customerOptin.getId());
        assertThat(customerOptinExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOptin> customerOptinList = customerOptinRepository.findAll();
        assertThat(customerOptinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOptin() throws Exception {
        // Initialize the database
        customerOptinService.save(customerOptin);

        // Search the customerOptin
        restCustomerOptinMockMvc.perform(get("/api/_search/customer-optins?query=id:" + customerOptin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOptin.getId().intValue())))
            .andExpect(jsonPath("$.[*].optinDate").value(hasItem(DEFAULT_OPTIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOptin.class);
    }
}
