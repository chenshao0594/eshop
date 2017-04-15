package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.repository.MerchantConfigurationRepository;
import com.smartshop.eshop.service.MerchantConfigurationService;
import com.smartshop.eshop.repository.search.MerchantConfigurationSearchRepository;
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

import com.smartshop.eshop.domain.enumeration.MerchantConfigurationType;
/**
 * Test class for the MerchantConfigurationResource REST controller.
 *
 * @see MerchantConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class MerchantConfigurationControllerIntTest {

    private static final MerchantConfigurationType DEFAULT_MERCHANT_CONFIGURATION_TYPE = MerchantConfigurationType.INTEGRATION;
    private static final MerchantConfigurationType UPDATED_MERCHANT_CONFIGURATION_TYPE = MerchantConfigurationType.SHOP;

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MerchantConfigurationRepository merchantConfigurationRepository;

    @Autowired
    private MerchantConfigurationService merchantConfigurationService;

    @Autowired
    private MerchantConfigurationSearchRepository merchantConfigurationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantConfigurationMockMvc;

    private MerchantConfiguration merchantConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MerchantConfigurationController merchantConfigurationController = new MerchantConfigurationController(merchantConfigurationService);
        this.restMerchantConfigurationMockMvc = MockMvcBuilders.standaloneSetup(merchantConfigurationController)
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
    public static MerchantConfiguration createEntity(EntityManager em) {
        MerchantConfiguration merchantConfiguration = new MerchantConfiguration()
            .merchantConfigurationType(DEFAULT_MERCHANT_CONFIGURATION_TYPE)
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return merchantConfiguration;
    }

    @Before
    public void initTest() {
        merchantConfigurationSearchRepository.deleteAll();
        merchantConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantConfiguration() throws Exception {
        int databaseSizeBeforeCreate = merchantConfigurationRepository.findAll().size();

        // Create the MerchantConfiguration
        restMerchantConfigurationMockMvc.perform(post("/api/merchant-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantConfiguration)))
            .andExpect(status().isCreated());

        // Validate the MerchantConfiguration in the database
        List<MerchantConfiguration> merchantConfigurationList = merchantConfigurationRepository.findAll();
        assertThat(merchantConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantConfiguration testMerchantConfiguration = merchantConfigurationList.get(merchantConfigurationList.size() - 1);
        assertThat(testMerchantConfiguration.getMerchantConfigurationType()).isEqualTo(DEFAULT_MERCHANT_CONFIGURATION_TYPE);
        assertThat(testMerchantConfiguration.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testMerchantConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the MerchantConfiguration in Elasticsearch
        MerchantConfiguration merchantConfigurationEs = merchantConfigurationSearchRepository.findOne(testMerchantConfiguration.getId());
        assertThat(merchantConfigurationEs).isEqualToComparingFieldByField(testMerchantConfiguration);
    }

    @Test
    @Transactional
    public void createMerchantConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantConfigurationRepository.findAll().size();

        // Create the MerchantConfiguration with an existing ID
        merchantConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantConfigurationMockMvc.perform(post("/api/merchant-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MerchantConfiguration> merchantConfigurationList = merchantConfigurationRepository.findAll();
        assertThat(merchantConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMerchantConfigurations() throws Exception {
        // Initialize the database
        merchantConfigurationRepository.saveAndFlush(merchantConfiguration);

        // Get all the merchantConfigurationList
        restMerchantConfigurationMockMvc.perform(get("/api/merchant-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].merchantConfigurationType").value(hasItem(DEFAULT_MERCHANT_CONFIGURATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getMerchantConfiguration() throws Exception {
        // Initialize the database
        merchantConfigurationRepository.saveAndFlush(merchantConfiguration);

        // Get the merchantConfiguration
        restMerchantConfigurationMockMvc.perform(get("/api/merchant-configurations/{id}", merchantConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.merchantConfigurationType").value(DEFAULT_MERCHANT_CONFIGURATION_TYPE.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMerchantConfiguration() throws Exception {
        // Get the merchantConfiguration
        restMerchantConfigurationMockMvc.perform(get("/api/merchant-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantConfiguration() throws Exception {
        // Initialize the database
        merchantConfigurationService.save(merchantConfiguration);

        int databaseSizeBeforeUpdate = merchantConfigurationRepository.findAll().size();

        // Update the merchantConfiguration
        MerchantConfiguration updatedMerchantConfiguration = merchantConfigurationRepository.findOne(merchantConfiguration.getId());
        updatedMerchantConfiguration
            .merchantConfigurationType(UPDATED_MERCHANT_CONFIGURATION_TYPE)
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restMerchantConfigurationMockMvc.perform(put("/api/merchant-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantConfiguration)))
            .andExpect(status().isOk());

        // Validate the MerchantConfiguration in the database
        List<MerchantConfiguration> merchantConfigurationList = merchantConfigurationRepository.findAll();
        assertThat(merchantConfigurationList).hasSize(databaseSizeBeforeUpdate);
        MerchantConfiguration testMerchantConfiguration = merchantConfigurationList.get(merchantConfigurationList.size() - 1);
        assertThat(testMerchantConfiguration.getMerchantConfigurationType()).isEqualTo(UPDATED_MERCHANT_CONFIGURATION_TYPE);
        assertThat(testMerchantConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testMerchantConfiguration.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the MerchantConfiguration in Elasticsearch
        MerchantConfiguration merchantConfigurationEs = merchantConfigurationSearchRepository.findOne(testMerchantConfiguration.getId());
        assertThat(merchantConfigurationEs).isEqualToComparingFieldByField(testMerchantConfiguration);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = merchantConfigurationRepository.findAll().size();

        // Create the MerchantConfiguration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantConfigurationMockMvc.perform(put("/api/merchant-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantConfiguration)))
            .andExpect(status().isCreated());

        // Validate the MerchantConfiguration in the database
        List<MerchantConfiguration> merchantConfigurationList = merchantConfigurationRepository.findAll();
        assertThat(merchantConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchantConfiguration() throws Exception {
        // Initialize the database
        merchantConfigurationService.save(merchantConfiguration);

        int databaseSizeBeforeDelete = merchantConfigurationRepository.findAll().size();

        // Get the merchantConfiguration
        restMerchantConfigurationMockMvc.perform(delete("/api/merchant-configurations/{id}", merchantConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean merchantConfigurationExistsInEs = merchantConfigurationSearchRepository.exists(merchantConfiguration.getId());
        assertThat(merchantConfigurationExistsInEs).isFalse();

        // Validate the database is empty
        List<MerchantConfiguration> merchantConfigurationList = merchantConfigurationRepository.findAll();
        assertThat(merchantConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMerchantConfiguration() throws Exception {
        // Initialize the database
        merchantConfigurationService.save(merchantConfiguration);

        // Search the merchantConfiguration
        restMerchantConfigurationMockMvc.perform(get("/api/_search/merchant-configurations?query=id:" + merchantConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].merchantConfigurationType").value(hasItem(DEFAULT_MERCHANT_CONFIGURATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantConfiguration.class);
    }
}
