package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CustomerOption;
import com.smartshop.eshop.repository.CustomerOptionRepository;
import com.smartshop.eshop.service.CustomerOptionService;
import com.smartshop.eshop.repository.search.CustomerOptionSearchRepository;
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
 * Test class for the CustomerOptionResource REST controller.
 *
 * @see CustomerOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CustomerOptionControllerIntTest {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_CUSTOMER_OPTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_OPTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLIC_OPTION = false;
    private static final Boolean UPDATED_PUBLIC_OPTION = true;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    @Autowired
    private CustomerOptionRepository customerOptionRepository;

    @Autowired
    private CustomerOptionService customerOptionService;

    @Autowired
    private CustomerOptionSearchRepository customerOptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerOptionMockMvc;

    private CustomerOption customerOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerOptionController customerOptionController = new CustomerOptionController(customerOptionService);
        this.restCustomerOptionMockMvc = MockMvcBuilders.standaloneSetup(customerOptionController)
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
    public static CustomerOption createEntity(EntityManager em) {
        CustomerOption customerOption = new CustomerOption()
            .active(DEFAULT_ACTIVE)
            .customerOptionType(DEFAULT_CUSTOMER_OPTION_TYPE)
            .code(DEFAULT_CODE)
            .publicOption(DEFAULT_PUBLIC_OPTION)
            .sortOrder(DEFAULT_SORT_ORDER);
        return customerOption;
    }

    @Before
    public void initTest() {
        customerOptionSearchRepository.deleteAll();
        customerOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerOption() throws Exception {
        int databaseSizeBeforeCreate = customerOptionRepository.findAll().size();

        // Create the CustomerOption
        restCustomerOptionMockMvc.perform(post("/api/customer-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOption)))
            .andExpect(status().isCreated());

        // Validate the CustomerOption in the database
        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerOption testCustomerOption = customerOptionList.get(customerOptionList.size() - 1);
        assertThat(testCustomerOption.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testCustomerOption.getCustomerOptionType()).isEqualTo(DEFAULT_CUSTOMER_OPTION_TYPE);
        assertThat(testCustomerOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerOption.isPublicOption()).isEqualTo(DEFAULT_PUBLIC_OPTION);
        assertThat(testCustomerOption.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);

        // Validate the CustomerOption in Elasticsearch
        CustomerOption customerOptionEs = customerOptionSearchRepository.findOne(testCustomerOption.getId());
        assertThat(customerOptionEs).isEqualToComparingFieldByField(testCustomerOption);
    }

    @Test
    @Transactional
    public void createCustomerOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerOptionRepository.findAll().size();

        // Create the CustomerOption with an existing ID
        customerOption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerOptionMockMvc.perform(post("/api/customer-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOption)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerOptionRepository.findAll().size();
        // set the field null
        customerOption.setCode(null);

        // Create the CustomerOption, which fails.

        restCustomerOptionMockMvc.perform(post("/api/customer-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOption)))
            .andExpect(status().isBadRequest());

        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerOptions() throws Exception {
        // Initialize the database
        customerOptionRepository.saveAndFlush(customerOption);

        // Get all the customerOptionList
        restCustomerOptionMockMvc.perform(get("/api/customer-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].customerOptionType").value(hasItem(DEFAULT_CUSTOMER_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].publicOption").value(hasItem(DEFAULT_PUBLIC_OPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getCustomerOption() throws Exception {
        // Initialize the database
        customerOptionRepository.saveAndFlush(customerOption);

        // Get the customerOption
        restCustomerOptionMockMvc.perform(get("/api/customer-options/{id}", customerOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerOption.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.customerOptionType").value(DEFAULT_CUSTOMER_OPTION_TYPE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.publicOption").value(DEFAULT_PUBLIC_OPTION.booleanValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerOption() throws Exception {
        // Get the customerOption
        restCustomerOptionMockMvc.perform(get("/api/customer-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerOption() throws Exception {
        // Initialize the database
        customerOptionService.save(customerOption);

        int databaseSizeBeforeUpdate = customerOptionRepository.findAll().size();

        // Update the customerOption
        CustomerOption updatedCustomerOption = customerOptionRepository.findOne(customerOption.getId());
        updatedCustomerOption
            .active(UPDATED_ACTIVE)
            .customerOptionType(UPDATED_CUSTOMER_OPTION_TYPE)
            .code(UPDATED_CODE)
            .publicOption(UPDATED_PUBLIC_OPTION)
            .sortOrder(UPDATED_SORT_ORDER);

        restCustomerOptionMockMvc.perform(put("/api/customer-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerOption)))
            .andExpect(status().isOk());

        // Validate the CustomerOption in the database
        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeUpdate);
        CustomerOption testCustomerOption = customerOptionList.get(customerOptionList.size() - 1);
        assertThat(testCustomerOption.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testCustomerOption.getCustomerOptionType()).isEqualTo(UPDATED_CUSTOMER_OPTION_TYPE);
        assertThat(testCustomerOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerOption.isPublicOption()).isEqualTo(UPDATED_PUBLIC_OPTION);
        assertThat(testCustomerOption.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);

        // Validate the CustomerOption in Elasticsearch
        CustomerOption customerOptionEs = customerOptionSearchRepository.findOne(testCustomerOption.getId());
        assertThat(customerOptionEs).isEqualToComparingFieldByField(testCustomerOption);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerOption() throws Exception {
        int databaseSizeBeforeUpdate = customerOptionRepository.findAll().size();

        // Create the CustomerOption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerOptionMockMvc.perform(put("/api/customer-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerOption)))
            .andExpect(status().isCreated());

        // Validate the CustomerOption in the database
        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerOption() throws Exception {
        // Initialize the database
        customerOptionService.save(customerOption);

        int databaseSizeBeforeDelete = customerOptionRepository.findAll().size();

        // Get the customerOption
        restCustomerOptionMockMvc.perform(delete("/api/customer-options/{id}", customerOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean customerOptionExistsInEs = customerOptionSearchRepository.exists(customerOption.getId());
        assertThat(customerOptionExistsInEs).isFalse();

        // Validate the database is empty
        List<CustomerOption> customerOptionList = customerOptionRepository.findAll();
        assertThat(customerOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCustomerOption() throws Exception {
        // Initialize the database
        customerOptionService.save(customerOption);

        // Search the customerOption
        restCustomerOptionMockMvc.perform(get("/api/_search/customer-options?query=id:" + customerOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].customerOptionType").value(hasItem(DEFAULT_CUSTOMER_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].publicOption").value(hasItem(DEFAULT_PUBLIC_OPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerOption.class);
    }
}
