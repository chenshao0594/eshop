package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ManufacturerDescription;
import com.smartshop.eshop.repository.ManufacturerDescriptionRepository;
import com.smartshop.eshop.service.ManufacturerDescriptionService;
import com.smartshop.eshop.repository.search.ManufacturerDescriptionSearchRepository;
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
 * Test class for the ManufacturerDescriptionResource REST controller.
 *
 * @see ManufacturerDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ManufacturerDescriptionControllerIntTest {

    private static final Integer DEFAULT_URL_CLICKED = 1;
    private static final Integer UPDATED_URL_CLICKED = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_LAST_CLICK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_CLICK = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ManufacturerDescriptionRepository manufacturerDescriptionRepository;

    @Autowired
    private ManufacturerDescriptionService manufacturerDescriptionService;

    @Autowired
    private ManufacturerDescriptionSearchRepository manufacturerDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restManufacturerDescriptionMockMvc;

    private ManufacturerDescription manufacturerDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManufacturerDescriptionController manufacturerDescriptionController = new ManufacturerDescriptionController(manufacturerDescriptionService);
        this.restManufacturerDescriptionMockMvc = MockMvcBuilders.standaloneSetup(manufacturerDescriptionController)
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
    public static ManufacturerDescription createEntity(EntityManager em) {
        ManufacturerDescription manufacturerDescription = new ManufacturerDescription()
            .urlClicked(DEFAULT_URL_CLICKED)
            .title(DEFAULT_TITLE)
            .url(DEFAULT_URL)
            .name(DEFAULT_NAME)
            .dateLastClick(DEFAULT_DATE_LAST_CLICK)
            .description(DEFAULT_DESCRIPTION);
        return manufacturerDescription;
    }

    @Before
    public void initTest() {
        manufacturerDescriptionSearchRepository.deleteAll();
        manufacturerDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createManufacturerDescription() throws Exception {
        int databaseSizeBeforeCreate = manufacturerDescriptionRepository.findAll().size();

        // Create the ManufacturerDescription
        restManufacturerDescriptionMockMvc.perform(post("/api/manufacturer-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDescription)))
            .andExpect(status().isCreated());

        // Validate the ManufacturerDescription in the database
        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ManufacturerDescription testManufacturerDescription = manufacturerDescriptionList.get(manufacturerDescriptionList.size() - 1);
        assertThat(testManufacturerDescription.getUrlClicked()).isEqualTo(DEFAULT_URL_CLICKED);
        assertThat(testManufacturerDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testManufacturerDescription.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testManufacturerDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testManufacturerDescription.getDateLastClick()).isEqualTo(DEFAULT_DATE_LAST_CLICK);
        assertThat(testManufacturerDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ManufacturerDescription in Elasticsearch
        ManufacturerDescription manufacturerDescriptionEs = manufacturerDescriptionSearchRepository.findOne(testManufacturerDescription.getId());
        assertThat(manufacturerDescriptionEs).isEqualToComparingFieldByField(testManufacturerDescription);
    }

    @Test
    @Transactional
    public void createManufacturerDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manufacturerDescriptionRepository.findAll().size();

        // Create the ManufacturerDescription with an existing ID
        manufacturerDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufacturerDescriptionMockMvc.perform(post("/api/manufacturer-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = manufacturerDescriptionRepository.findAll().size();
        // set the field null
        manufacturerDescription.setName(null);

        // Create the ManufacturerDescription, which fails.

        restManufacturerDescriptionMockMvc.perform(post("/api/manufacturer-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDescription)))
            .andExpect(status().isBadRequest());

        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManufacturerDescriptions() throws Exception {
        // Initialize the database
        manufacturerDescriptionRepository.saveAndFlush(manufacturerDescription);

        // Get all the manufacturerDescriptionList
        restManufacturerDescriptionMockMvc.perform(get("/api/manufacturer-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturerDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlClicked").value(hasItem(DEFAULT_URL_CLICKED)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateLastClick").value(hasItem(DEFAULT_DATE_LAST_CLICK.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getManufacturerDescription() throws Exception {
        // Initialize the database
        manufacturerDescriptionRepository.saveAndFlush(manufacturerDescription);

        // Get the manufacturerDescription
        restManufacturerDescriptionMockMvc.perform(get("/api/manufacturer-descriptions/{id}", manufacturerDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manufacturerDescription.getId().intValue()))
            .andExpect(jsonPath("$.urlClicked").value(DEFAULT_URL_CLICKED))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateLastClick").value(DEFAULT_DATE_LAST_CLICK.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManufacturerDescription() throws Exception {
        // Get the manufacturerDescription
        restManufacturerDescriptionMockMvc.perform(get("/api/manufacturer-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManufacturerDescription() throws Exception {
        // Initialize the database
        manufacturerDescriptionService.save(manufacturerDescription);

        int databaseSizeBeforeUpdate = manufacturerDescriptionRepository.findAll().size();

        // Update the manufacturerDescription
        ManufacturerDescription updatedManufacturerDescription = manufacturerDescriptionRepository.findOne(manufacturerDescription.getId());
        updatedManufacturerDescription
            .urlClicked(UPDATED_URL_CLICKED)
            .title(UPDATED_TITLE)
            .url(UPDATED_URL)
            .name(UPDATED_NAME)
            .dateLastClick(UPDATED_DATE_LAST_CLICK)
            .description(UPDATED_DESCRIPTION);

        restManufacturerDescriptionMockMvc.perform(put("/api/manufacturer-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedManufacturerDescription)))
            .andExpect(status().isOk());

        // Validate the ManufacturerDescription in the database
        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ManufacturerDescription testManufacturerDescription = manufacturerDescriptionList.get(manufacturerDescriptionList.size() - 1);
        assertThat(testManufacturerDescription.getUrlClicked()).isEqualTo(UPDATED_URL_CLICKED);
        assertThat(testManufacturerDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testManufacturerDescription.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testManufacturerDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testManufacturerDescription.getDateLastClick()).isEqualTo(UPDATED_DATE_LAST_CLICK);
        assertThat(testManufacturerDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ManufacturerDescription in Elasticsearch
        ManufacturerDescription manufacturerDescriptionEs = manufacturerDescriptionSearchRepository.findOne(testManufacturerDescription.getId());
        assertThat(manufacturerDescriptionEs).isEqualToComparingFieldByField(testManufacturerDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingManufacturerDescription() throws Exception {
        int databaseSizeBeforeUpdate = manufacturerDescriptionRepository.findAll().size();

        // Create the ManufacturerDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restManufacturerDescriptionMockMvc.perform(put("/api/manufacturer-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturerDescription)))
            .andExpect(status().isCreated());

        // Validate the ManufacturerDescription in the database
        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteManufacturerDescription() throws Exception {
        // Initialize the database
        manufacturerDescriptionService.save(manufacturerDescription);

        int databaseSizeBeforeDelete = manufacturerDescriptionRepository.findAll().size();

        // Get the manufacturerDescription
        restManufacturerDescriptionMockMvc.perform(delete("/api/manufacturer-descriptions/{id}", manufacturerDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean manufacturerDescriptionExistsInEs = manufacturerDescriptionSearchRepository.exists(manufacturerDescription.getId());
        assertThat(manufacturerDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ManufacturerDescription> manufacturerDescriptionList = manufacturerDescriptionRepository.findAll();
        assertThat(manufacturerDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchManufacturerDescription() throws Exception {
        // Initialize the database
        manufacturerDescriptionService.save(manufacturerDescription);

        // Search the manufacturerDescription
        restManufacturerDescriptionMockMvc.perform(get("/api/_search/manufacturer-descriptions?query=id:" + manufacturerDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturerDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].urlClicked").value(hasItem(DEFAULT_URL_CLICKED)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateLastClick").value(hasItem(DEFAULT_DATE_LAST_CLICK.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManufacturerDescription.class);
    }
}
