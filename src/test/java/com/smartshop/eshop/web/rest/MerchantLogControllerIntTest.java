package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.MerchantLog;
import com.smartshop.eshop.repository.MerchantLogRepository;
import com.smartshop.eshop.service.MerchantLogService;
import com.smartshop.eshop.repository.search.MerchantLogSearchRepository;
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
 * Test class for the MerchantLogResource REST controller.
 *
 * @see MerchantLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class MerchantLogControllerIntTest {

    private static final String DEFAULT_LOG = "AAAAAAAAAA";
    private static final String UPDATED_LOG = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    @Autowired
    private MerchantLogRepository merchantLogRepository;

    @Autowired
    private MerchantLogService merchantLogService;

    @Autowired
    private MerchantLogSearchRepository merchantLogSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantLogMockMvc;

    private MerchantLog merchantLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MerchantLogController merchantLogController = new MerchantLogController(merchantLogService);
        this.restMerchantLogMockMvc = MockMvcBuilders.standaloneSetup(merchantLogController)
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
    public static MerchantLog createEntity(EntityManager em) {
        MerchantLog merchantLog = new MerchantLog()
            .log(DEFAULT_LOG)
            .module(DEFAULT_MODULE);
        return merchantLog;
    }

    @Before
    public void initTest() {
        merchantLogSearchRepository.deleteAll();
        merchantLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantLog() throws Exception {
        int databaseSizeBeforeCreate = merchantLogRepository.findAll().size();

        // Create the MerchantLog
        restMerchantLogMockMvc.perform(post("/api/merchant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantLog)))
            .andExpect(status().isCreated());

        // Validate the MerchantLog in the database
        List<MerchantLog> merchantLogList = merchantLogRepository.findAll();
        assertThat(merchantLogList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantLog testMerchantLog = merchantLogList.get(merchantLogList.size() - 1);
        assertThat(testMerchantLog.getLog()).isEqualTo(DEFAULT_LOG);
        assertThat(testMerchantLog.getModule()).isEqualTo(DEFAULT_MODULE);

        // Validate the MerchantLog in Elasticsearch
        MerchantLog merchantLogEs = merchantLogSearchRepository.findOne(testMerchantLog.getId());
        assertThat(merchantLogEs).isEqualToComparingFieldByField(testMerchantLog);
    }

    @Test
    @Transactional
    public void createMerchantLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantLogRepository.findAll().size();

        // Create the MerchantLog with an existing ID
        merchantLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantLogMockMvc.perform(post("/api/merchant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantLog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MerchantLog> merchantLogList = merchantLogRepository.findAll();
        assertThat(merchantLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMerchantLogs() throws Exception {
        // Initialize the database
        merchantLogRepository.saveAndFlush(merchantLog);

        // Get all the merchantLogList
        restMerchantLogMockMvc.perform(get("/api/merchant-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].log").value(hasItem(DEFAULT_LOG.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())));
    }

    @Test
    @Transactional
    public void getMerchantLog() throws Exception {
        // Initialize the database
        merchantLogRepository.saveAndFlush(merchantLog);

        // Get the merchantLog
        restMerchantLogMockMvc.perform(get("/api/merchant-logs/{id}", merchantLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantLog.getId().intValue()))
            .andExpect(jsonPath("$.log").value(DEFAULT_LOG.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMerchantLog() throws Exception {
        // Get the merchantLog
        restMerchantLogMockMvc.perform(get("/api/merchant-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantLog() throws Exception {
        // Initialize the database
        merchantLogService.save(merchantLog);

        int databaseSizeBeforeUpdate = merchantLogRepository.findAll().size();

        // Update the merchantLog
        MerchantLog updatedMerchantLog = merchantLogRepository.findOne(merchantLog.getId());
        updatedMerchantLog
            .log(UPDATED_LOG)
            .module(UPDATED_MODULE);

        restMerchantLogMockMvc.perform(put("/api/merchant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantLog)))
            .andExpect(status().isOk());

        // Validate the MerchantLog in the database
        List<MerchantLog> merchantLogList = merchantLogRepository.findAll();
        assertThat(merchantLogList).hasSize(databaseSizeBeforeUpdate);
        MerchantLog testMerchantLog = merchantLogList.get(merchantLogList.size() - 1);
        assertThat(testMerchantLog.getLog()).isEqualTo(UPDATED_LOG);
        assertThat(testMerchantLog.getModule()).isEqualTo(UPDATED_MODULE);

        // Validate the MerchantLog in Elasticsearch
        MerchantLog merchantLogEs = merchantLogSearchRepository.findOne(testMerchantLog.getId());
        assertThat(merchantLogEs).isEqualToComparingFieldByField(testMerchantLog);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantLog() throws Exception {
        int databaseSizeBeforeUpdate = merchantLogRepository.findAll().size();

        // Create the MerchantLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantLogMockMvc.perform(put("/api/merchant-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantLog)))
            .andExpect(status().isCreated());

        // Validate the MerchantLog in the database
        List<MerchantLog> merchantLogList = merchantLogRepository.findAll();
        assertThat(merchantLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchantLog() throws Exception {
        // Initialize the database
        merchantLogService.save(merchantLog);

        int databaseSizeBeforeDelete = merchantLogRepository.findAll().size();

        // Get the merchantLog
        restMerchantLogMockMvc.perform(delete("/api/merchant-logs/{id}", merchantLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean merchantLogExistsInEs = merchantLogSearchRepository.exists(merchantLog.getId());
        assertThat(merchantLogExistsInEs).isFalse();

        // Validate the database is empty
        List<MerchantLog> merchantLogList = merchantLogRepository.findAll();
        assertThat(merchantLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMerchantLog() throws Exception {
        // Initialize the database
        merchantLogService.save(merchantLog);

        // Search the merchantLog
        restMerchantLogMockMvc.perform(get("/api/_search/merchant-logs?query=id:" + merchantLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].log").value(hasItem(DEFAULT_LOG.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantLog.class);
    }
}
