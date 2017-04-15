package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.SystemConfiguration;
import com.smartshop.eshop.repository.SystemConfigurationRepository;
import com.smartshop.eshop.service.SystemConfigurationService;
import com.smartshop.eshop.repository.search.SystemConfigurationSearchRepository;
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
 * Test class for the SystemConfigurationResource REST controller.
 *
 * @see SystemConfigurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class SystemConfigurationControllerIntTest {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SystemConfigurationRepository systemConfigurationRepository;

    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private SystemConfigurationSearchRepository systemConfigurationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemConfigurationMockMvc;

    private SystemConfiguration systemConfiguration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SystemConfigurationController systemConfigurationController = new SystemConfigurationController(systemConfigurationService);
        this.restSystemConfigurationMockMvc = MockMvcBuilders.standaloneSetup(systemConfigurationController)
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
    public static SystemConfiguration createEntity(EntityManager em) {
        SystemConfiguration systemConfiguration = new SystemConfiguration()
            .key(DEFAULT_KEY)
            .value(DEFAULT_VALUE);
        return systemConfiguration;
    }

    @Before
    public void initTest() {
        systemConfigurationSearchRepository.deleteAll();
        systemConfiguration = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemConfiguration() throws Exception {
        int databaseSizeBeforeCreate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration
        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfiguration)))
            .andExpect(status().isCreated());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeCreate + 1);
        SystemConfiguration testSystemConfiguration = systemConfigurationList.get(systemConfigurationList.size() - 1);
        assertThat(testSystemConfiguration.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSystemConfiguration.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the SystemConfiguration in Elasticsearch
        SystemConfiguration systemConfigurationEs = systemConfigurationSearchRepository.findOne(testSystemConfiguration.getId());
        assertThat(systemConfigurationEs).isEqualToComparingFieldByField(testSystemConfiguration);
    }

    @Test
    @Transactional
    public void createSystemConfigurationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration with an existing ID
        systemConfiguration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemConfigurationMockMvc.perform(post("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfiguration)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSystemConfigurations() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        // Get all the systemConfigurationList
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationRepository.saveAndFlush(systemConfiguration);

        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations/{id}", systemConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystemConfiguration() throws Exception {
        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(get("/api/system-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationService.save(systemConfiguration);

        int databaseSizeBeforeUpdate = systemConfigurationRepository.findAll().size();

        // Update the systemConfiguration
        SystemConfiguration updatedSystemConfiguration = systemConfigurationRepository.findOne(systemConfiguration.getId());
        updatedSystemConfiguration
            .key(UPDATED_KEY)
            .value(UPDATED_VALUE);

        restSystemConfigurationMockMvc.perform(put("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystemConfiguration)))
            .andExpect(status().isOk());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeUpdate);
        SystemConfiguration testSystemConfiguration = systemConfigurationList.get(systemConfigurationList.size() - 1);
        assertThat(testSystemConfiguration.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSystemConfiguration.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the SystemConfiguration in Elasticsearch
        SystemConfiguration systemConfigurationEs = systemConfigurationSearchRepository.findOne(testSystemConfiguration.getId());
        assertThat(systemConfigurationEs).isEqualToComparingFieldByField(testSystemConfiguration);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemConfiguration() throws Exception {
        int databaseSizeBeforeUpdate = systemConfigurationRepository.findAll().size();

        // Create the SystemConfiguration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSystemConfigurationMockMvc.perform(put("/api/system-configurations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemConfiguration)))
            .andExpect(status().isCreated());

        // Validate the SystemConfiguration in the database
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationService.save(systemConfiguration);

        int databaseSizeBeforeDelete = systemConfigurationRepository.findAll().size();

        // Get the systemConfiguration
        restSystemConfigurationMockMvc.perform(delete("/api/system-configurations/{id}", systemConfiguration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean systemConfigurationExistsInEs = systemConfigurationSearchRepository.exists(systemConfiguration.getId());
        assertThat(systemConfigurationExistsInEs).isFalse();

        // Validate the database is empty
        List<SystemConfiguration> systemConfigurationList = systemConfigurationRepository.findAll();
        assertThat(systemConfigurationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSystemConfiguration() throws Exception {
        // Initialize the database
        systemConfigurationService.save(systemConfiguration);

        // Search the systemConfiguration
        restSystemConfigurationMockMvc.perform(get("/api/_search/system-configurations?query=id:" + systemConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemConfiguration.class);
    }
}
