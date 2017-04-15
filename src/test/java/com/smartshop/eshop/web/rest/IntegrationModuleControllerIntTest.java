package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.repository.IntegrationModuleRepository;
import com.smartshop.eshop.service.IntegrationModuleService;
import com.smartshop.eshop.repository.search.IntegrationModuleSearchRepository;
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
 * Test class for the IntegrationModuleResource REST controller.
 *
 * @see IntegrationModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class IntegrationModuleControllerIntTest {

    private static final String DEFAULT_CONFIG_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CUSTOM_MODULE = false;
    private static final Boolean UPDATED_CUSTOM_MODULE = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGIONS = "AAAAAAAAAA";
    private static final String UPDATED_REGIONS = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    @Autowired
    private IntegrationModuleRepository integrationModuleRepository;

    @Autowired
    private IntegrationModuleService integrationModuleService;

    @Autowired
    private IntegrationModuleSearchRepository integrationModuleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntegrationModuleMockMvc;

    private IntegrationModule integrationModule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IntegrationModuleController integrationModuleController = new IntegrationModuleController(integrationModuleService);
        this.restIntegrationModuleMockMvc = MockMvcBuilders.standaloneSetup(integrationModuleController)
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
    public static IntegrationModule createEntity(EntityManager em) {
        IntegrationModule integrationModule = new IntegrationModule()
            .configDetails(DEFAULT_CONFIG_DETAILS)
            .customModule(DEFAULT_CUSTOM_MODULE)
            .type(DEFAULT_TYPE)
            .code(DEFAULT_CODE)
            .regions(DEFAULT_REGIONS)
            .image(DEFAULT_IMAGE)
            .module(DEFAULT_MODULE)
            .configuration(DEFAULT_CONFIGURATION);
        return integrationModule;
    }

    @Before
    public void initTest() {
        integrationModuleSearchRepository.deleteAll();
        integrationModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntegrationModule() throws Exception {
        int databaseSizeBeforeCreate = integrationModuleRepository.findAll().size();

        // Create the IntegrationModule
        restIntegrationModuleMockMvc.perform(post("/api/integration-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationModule)))
            .andExpect(status().isCreated());

        // Validate the IntegrationModule in the database
        List<IntegrationModule> integrationModuleList = integrationModuleRepository.findAll();
        assertThat(integrationModuleList).hasSize(databaseSizeBeforeCreate + 1);
        IntegrationModule testIntegrationModule = integrationModuleList.get(integrationModuleList.size() - 1);
        assertThat(testIntegrationModule.getConfigDetails()).isEqualTo(DEFAULT_CONFIG_DETAILS);
        assertThat(testIntegrationModule.isCustomModule()).isEqualTo(DEFAULT_CUSTOM_MODULE);
        assertThat(testIntegrationModule.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIntegrationModule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIntegrationModule.getRegions()).isEqualTo(DEFAULT_REGIONS);
        assertThat(testIntegrationModule.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testIntegrationModule.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testIntegrationModule.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);

        // Validate the IntegrationModule in Elasticsearch
        IntegrationModule integrationModuleEs = integrationModuleSearchRepository.findOne(testIntegrationModule.getId());
        assertThat(integrationModuleEs).isEqualToComparingFieldByField(testIntegrationModule);
    }

    @Test
    @Transactional
    public void createIntegrationModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = integrationModuleRepository.findAll().size();

        // Create the IntegrationModule with an existing ID
        integrationModule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrationModuleMockMvc.perform(post("/api/integration-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationModule)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<IntegrationModule> integrationModuleList = integrationModuleRepository.findAll();
        assertThat(integrationModuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIntegrationModules() throws Exception {
        // Initialize the database
        integrationModuleRepository.saveAndFlush(integrationModule);

        // Get all the integrationModuleList
        restIntegrationModuleMockMvc.perform(get("/api/integration-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].configDetails").value(hasItem(DEFAULT_CONFIG_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].customModule").value(hasItem(DEFAULT_CUSTOM_MODULE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION.toString())));
    }

    @Test
    @Transactional
    public void getIntegrationModule() throws Exception {
        // Initialize the database
        integrationModuleRepository.saveAndFlush(integrationModule);

        // Get the integrationModule
        restIntegrationModuleMockMvc.perform(get("/api/integration-modules/{id}", integrationModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(integrationModule.getId().intValue()))
            .andExpect(jsonPath("$.configDetails").value(DEFAULT_CONFIG_DETAILS.toString()))
            .andExpect(jsonPath("$.customModule").value(DEFAULT_CUSTOM_MODULE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.regions").value(DEFAULT_REGIONS.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIntegrationModule() throws Exception {
        // Get the integrationModule
        restIntegrationModuleMockMvc.perform(get("/api/integration-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrationModule() throws Exception {
        // Initialize the database
        integrationModuleService.save(integrationModule);

        int databaseSizeBeforeUpdate = integrationModuleRepository.findAll().size();

        // Update the integrationModule
        IntegrationModule updatedIntegrationModule = integrationModuleRepository.findOne(integrationModule.getId());
        updatedIntegrationModule
            .configDetails(UPDATED_CONFIG_DETAILS)
            .customModule(UPDATED_CUSTOM_MODULE)
            .type(UPDATED_TYPE)
            .code(UPDATED_CODE)
            .regions(UPDATED_REGIONS)
            .image(UPDATED_IMAGE)
            .module(UPDATED_MODULE)
            .configuration(UPDATED_CONFIGURATION);

        restIntegrationModuleMockMvc.perform(put("/api/integration-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntegrationModule)))
            .andExpect(status().isOk());

        // Validate the IntegrationModule in the database
        List<IntegrationModule> integrationModuleList = integrationModuleRepository.findAll();
        assertThat(integrationModuleList).hasSize(databaseSizeBeforeUpdate);
        IntegrationModule testIntegrationModule = integrationModuleList.get(integrationModuleList.size() - 1);
        assertThat(testIntegrationModule.getConfigDetails()).isEqualTo(UPDATED_CONFIG_DETAILS);
        assertThat(testIntegrationModule.isCustomModule()).isEqualTo(UPDATED_CUSTOM_MODULE);
        assertThat(testIntegrationModule.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIntegrationModule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIntegrationModule.getRegions()).isEqualTo(UPDATED_REGIONS);
        assertThat(testIntegrationModule.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testIntegrationModule.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testIntegrationModule.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);

        // Validate the IntegrationModule in Elasticsearch
        IntegrationModule integrationModuleEs = integrationModuleSearchRepository.findOne(testIntegrationModule.getId());
        assertThat(integrationModuleEs).isEqualToComparingFieldByField(testIntegrationModule);
    }

    @Test
    @Transactional
    public void updateNonExistingIntegrationModule() throws Exception {
        int databaseSizeBeforeUpdate = integrationModuleRepository.findAll().size();

        // Create the IntegrationModule

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntegrationModuleMockMvc.perform(put("/api/integration-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrationModule)))
            .andExpect(status().isCreated());

        // Validate the IntegrationModule in the database
        List<IntegrationModule> integrationModuleList = integrationModuleRepository.findAll();
        assertThat(integrationModuleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIntegrationModule() throws Exception {
        // Initialize the database
        integrationModuleService.save(integrationModule);

        int databaseSizeBeforeDelete = integrationModuleRepository.findAll().size();

        // Get the integrationModule
        restIntegrationModuleMockMvc.perform(delete("/api/integration-modules/{id}", integrationModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean integrationModuleExistsInEs = integrationModuleSearchRepository.exists(integrationModule.getId());
        assertThat(integrationModuleExistsInEs).isFalse();

        // Validate the database is empty
        List<IntegrationModule> integrationModuleList = integrationModuleRepository.findAll();
        assertThat(integrationModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIntegrationModule() throws Exception {
        // Initialize the database
        integrationModuleService.save(integrationModule);

        // Search the integrationModule
        restIntegrationModuleMockMvc.perform(get("/api/_search/integration-modules?query=id:" + integrationModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrationModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].configDetails").value(hasItem(DEFAULT_CONFIG_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].customModule").value(hasItem(DEFAULT_CUSTOM_MODULE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].regions").value(hasItem(DEFAULT_REGIONS.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrationModule.class);
    }
}
