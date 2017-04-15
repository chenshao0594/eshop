package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.SystemNotification;
import com.smartshop.eshop.repository.SystemNotificationRepository;
import com.smartshop.eshop.service.SystemNotificationService;
import com.smartshop.eshop.repository.search.SystemNotificationSearchRepository;
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
 * Test class for the SystemNotificationResource REST controller.
 *
 * @see SystemNotificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class SystemNotificationControllerIntTest {

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

    @Autowired
    private SystemNotificationService systemNotificationService;

    @Autowired
    private SystemNotificationSearchRepository systemNotificationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSystemNotificationMockMvc;

    private SystemNotification systemNotification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SystemNotificationController systemNotificationController = new SystemNotificationController(systemNotificationService);
        this.restSystemNotificationMockMvc = MockMvcBuilders.standaloneSetup(systemNotificationController)
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
    public static SystemNotification createEntity(EntityManager em) {
        SystemNotification systemNotification = new SystemNotification()
            .endDate(DEFAULT_END_DATE)
            .key(DEFAULT_KEY)
            .startDate(DEFAULT_START_DATE)
            .value(DEFAULT_VALUE);
        return systemNotification;
    }

    @Before
    public void initTest() {
        systemNotificationSearchRepository.deleteAll();
        systemNotification = createEntity(em);
    }

    @Test
    @Transactional
    public void createSystemNotification() throws Exception {
        int databaseSizeBeforeCreate = systemNotificationRepository.findAll().size();

        // Create the SystemNotification
        restSystemNotificationMockMvc.perform(post("/api/system-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemNotification)))
            .andExpect(status().isCreated());

        // Validate the SystemNotification in the database
        List<SystemNotification> systemNotificationList = systemNotificationRepository.findAll();
        assertThat(systemNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        SystemNotification testSystemNotification = systemNotificationList.get(systemNotificationList.size() - 1);
        assertThat(testSystemNotification.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testSystemNotification.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSystemNotification.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testSystemNotification.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the SystemNotification in Elasticsearch
        SystemNotification systemNotificationEs = systemNotificationSearchRepository.findOne(testSystemNotification.getId());
        assertThat(systemNotificationEs).isEqualToComparingFieldByField(testSystemNotification);
    }

    @Test
    @Transactional
    public void createSystemNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = systemNotificationRepository.findAll().size();

        // Create the SystemNotification with an existing ID
        systemNotification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemNotificationMockMvc.perform(post("/api/system-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemNotification)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SystemNotification> systemNotificationList = systemNotificationRepository.findAll();
        assertThat(systemNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSystemNotifications() throws Exception {
        // Initialize the database
        systemNotificationRepository.saveAndFlush(systemNotification);

        // Get all the systemNotificationList
        restSystemNotificationMockMvc.perform(get("/api/system-notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getSystemNotification() throws Exception {
        // Initialize the database
        systemNotificationRepository.saveAndFlush(systemNotification);

        // Get the systemNotification
        restSystemNotificationMockMvc.perform(get("/api/system-notifications/{id}", systemNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(systemNotification.getId().intValue()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSystemNotification() throws Exception {
        // Get the systemNotification
        restSystemNotificationMockMvc.perform(get("/api/system-notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSystemNotification() throws Exception {
        // Initialize the database
        systemNotificationService.save(systemNotification);

        int databaseSizeBeforeUpdate = systemNotificationRepository.findAll().size();

        // Update the systemNotification
        SystemNotification updatedSystemNotification = systemNotificationRepository.findOne(systemNotification.getId());
        updatedSystemNotification
            .endDate(UPDATED_END_DATE)
            .key(UPDATED_KEY)
            .startDate(UPDATED_START_DATE)
            .value(UPDATED_VALUE);

        restSystemNotificationMockMvc.perform(put("/api/system-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSystemNotification)))
            .andExpect(status().isOk());

        // Validate the SystemNotification in the database
        List<SystemNotification> systemNotificationList = systemNotificationRepository.findAll();
        assertThat(systemNotificationList).hasSize(databaseSizeBeforeUpdate);
        SystemNotification testSystemNotification = systemNotificationList.get(systemNotificationList.size() - 1);
        assertThat(testSystemNotification.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testSystemNotification.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSystemNotification.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testSystemNotification.getValue()).isEqualTo(UPDATED_VALUE);

        // Validate the SystemNotification in Elasticsearch
        SystemNotification systemNotificationEs = systemNotificationSearchRepository.findOne(testSystemNotification.getId());
        assertThat(systemNotificationEs).isEqualToComparingFieldByField(testSystemNotification);
    }

    @Test
    @Transactional
    public void updateNonExistingSystemNotification() throws Exception {
        int databaseSizeBeforeUpdate = systemNotificationRepository.findAll().size();

        // Create the SystemNotification

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSystemNotificationMockMvc.perform(put("/api/system-notifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(systemNotification)))
            .andExpect(status().isCreated());

        // Validate the SystemNotification in the database
        List<SystemNotification> systemNotificationList = systemNotificationRepository.findAll();
        assertThat(systemNotificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSystemNotification() throws Exception {
        // Initialize the database
        systemNotificationService.save(systemNotification);

        int databaseSizeBeforeDelete = systemNotificationRepository.findAll().size();

        // Get the systemNotification
        restSystemNotificationMockMvc.perform(delete("/api/system-notifications/{id}", systemNotification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean systemNotificationExistsInEs = systemNotificationSearchRepository.exists(systemNotification.getId());
        assertThat(systemNotificationExistsInEs).isFalse();

        // Validate the database is empty
        List<SystemNotification> systemNotificationList = systemNotificationRepository.findAll();
        assertThat(systemNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSystemNotification() throws Exception {
        // Initialize the database
        systemNotificationService.save(systemNotification);

        // Search the systemNotification
        restSystemNotificationMockMvc.perform(get("/api/_search/system-notifications?query=id:" + systemNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemNotification.class);
    }
}
