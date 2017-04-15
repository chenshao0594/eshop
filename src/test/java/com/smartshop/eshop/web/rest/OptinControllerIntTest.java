package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Optin;
import com.smartshop.eshop.repository.OptinRepository;
import com.smartshop.eshop.service.OptinService;
import com.smartshop.eshop.repository.search.OptinSearchRepository;
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
 * Test class for the OptinResource REST controller.
 *
 * @see OptinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OptinControllerIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OptinRepository optinRepository;

    @Autowired
    private OptinService optinService;

    @Autowired
    private OptinSearchRepository optinSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOptinMockMvc;

    private Optin optin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OptinController optinController = new OptinController(optinService);
        this.restOptinMockMvc = MockMvcBuilders.standaloneSetup(optinController)
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
    public static Optin createEntity(EntityManager em) {
        Optin optin = new Optin()
            .description(DEFAULT_DESCRIPTION)
            .startDate(DEFAULT_START_DATE)
            .code(DEFAULT_CODE)
            .endDate(DEFAULT_END_DATE);
        return optin;
    }

    @Before
    public void initTest() {
        optinSearchRepository.deleteAll();
        optin = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptin() throws Exception {
        int databaseSizeBeforeCreate = optinRepository.findAll().size();

        // Create the Optin
        restOptinMockMvc.perform(post("/api/optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optin)))
            .andExpect(status().isCreated());

        // Validate the Optin in the database
        List<Optin> optinList = optinRepository.findAll();
        assertThat(optinList).hasSize(databaseSizeBeforeCreate + 1);
        Optin testOptin = optinList.get(optinList.size() - 1);
        assertThat(testOptin.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOptin.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOptin.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOptin.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the Optin in Elasticsearch
        Optin optinEs = optinSearchRepository.findOne(testOptin.getId());
        assertThat(optinEs).isEqualToComparingFieldByField(testOptin);
    }

    @Test
    @Transactional
    public void createOptinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = optinRepository.findAll().size();

        // Create the Optin with an existing ID
        optin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptinMockMvc.perform(post("/api/optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Optin> optinList = optinRepository.findAll();
        assertThat(optinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOptins() throws Exception {
        // Initialize the database
        optinRepository.saveAndFlush(optin);

        // Get all the optinList
        restOptinMockMvc.perform(get("/api/optins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optin.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getOptin() throws Exception {
        // Initialize the database
        optinRepository.saveAndFlush(optin);

        // Get the optin
        restOptinMockMvc.perform(get("/api/optins/{id}", optin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optin.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOptin() throws Exception {
        // Get the optin
        restOptinMockMvc.perform(get("/api/optins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptin() throws Exception {
        // Initialize the database
        optinService.save(optin);

        int databaseSizeBeforeUpdate = optinRepository.findAll().size();

        // Update the optin
        Optin updatedOptin = optinRepository.findOne(optin.getId());
        updatedOptin
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .code(UPDATED_CODE)
            .endDate(UPDATED_END_DATE);

        restOptinMockMvc.perform(put("/api/optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptin)))
            .andExpect(status().isOk());

        // Validate the Optin in the database
        List<Optin> optinList = optinRepository.findAll();
        assertThat(optinList).hasSize(databaseSizeBeforeUpdate);
        Optin testOptin = optinList.get(optinList.size() - 1);
        assertThat(testOptin.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOptin.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOptin.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOptin.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the Optin in Elasticsearch
        Optin optinEs = optinSearchRepository.findOne(testOptin.getId());
        assertThat(optinEs).isEqualToComparingFieldByField(testOptin);
    }

    @Test
    @Transactional
    public void updateNonExistingOptin() throws Exception {
        int databaseSizeBeforeUpdate = optinRepository.findAll().size();

        // Create the Optin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOptinMockMvc.perform(put("/api/optins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optin)))
            .andExpect(status().isCreated());

        // Validate the Optin in the database
        List<Optin> optinList = optinRepository.findAll();
        assertThat(optinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOptin() throws Exception {
        // Initialize the database
        optinService.save(optin);

        int databaseSizeBeforeDelete = optinRepository.findAll().size();

        // Get the optin
        restOptinMockMvc.perform(delete("/api/optins/{id}", optin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean optinExistsInEs = optinSearchRepository.exists(optin.getId());
        assertThat(optinExistsInEs).isFalse();

        // Validate the database is empty
        List<Optin> optinList = optinRepository.findAll();
        assertThat(optinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOptin() throws Exception {
        // Initialize the database
        optinService.save(optin);

        // Search the optin
        restOptinMockMvc.perform(get("/api/_search/optins?query=id:" + optin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optin.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Optin.class);
    }
}
