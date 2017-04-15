package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.repository.ZoneDescriptionRepository;
import com.smartshop.eshop.service.ZoneDescriptionService;
import com.smartshop.eshop.repository.search.ZoneDescriptionSearchRepository;
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
 * Test class for the ZoneDescriptionResource REST controller.
 *
 * @see ZoneDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ZoneDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ZoneDescriptionRepository zoneDescriptionRepository;

    @Autowired
    private ZoneDescriptionService zoneDescriptionService;

    @Autowired
    private ZoneDescriptionSearchRepository zoneDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZoneDescriptionMockMvc;

    private ZoneDescription zoneDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ZoneDescriptionController zoneDescriptionController = new ZoneDescriptionController(zoneDescriptionService);
        this.restZoneDescriptionMockMvc = MockMvcBuilders.standaloneSetup(zoneDescriptionController)
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
    public static ZoneDescription createEntity(EntityManager em) {
        ZoneDescription zoneDescription = new ZoneDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return zoneDescription;
    }

    @Before
    public void initTest() {
        zoneDescriptionSearchRepository.deleteAll();
        zoneDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createZoneDescription() throws Exception {
        int databaseSizeBeforeCreate = zoneDescriptionRepository.findAll().size();

        // Create the ZoneDescription
        restZoneDescriptionMockMvc.perform(post("/api/zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneDescription)))
            .andExpect(status().isCreated());

        // Validate the ZoneDescription in the database
        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ZoneDescription testZoneDescription = zoneDescriptionList.get(zoneDescriptionList.size() - 1);
        assertThat(testZoneDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testZoneDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testZoneDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ZoneDescription in Elasticsearch
        ZoneDescription zoneDescriptionEs = zoneDescriptionSearchRepository.findOne(testZoneDescription.getId());
        assertThat(zoneDescriptionEs).isEqualToComparingFieldByField(testZoneDescription);
    }

    @Test
    @Transactional
    public void createZoneDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zoneDescriptionRepository.findAll().size();

        // Create the ZoneDescription with an existing ID
        zoneDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZoneDescriptionMockMvc.perform(post("/api/zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = zoneDescriptionRepository.findAll().size();
        // set the field null
        zoneDescription.setName(null);

        // Create the ZoneDescription, which fails.

        restZoneDescriptionMockMvc.perform(post("/api/zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneDescription)))
            .andExpect(status().isBadRequest());

        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZoneDescriptions() throws Exception {
        // Initialize the database
        zoneDescriptionRepository.saveAndFlush(zoneDescription);

        // Get all the zoneDescriptionList
        restZoneDescriptionMockMvc.perform(get("/api/zone-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zoneDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getZoneDescription() throws Exception {
        // Initialize the database
        zoneDescriptionRepository.saveAndFlush(zoneDescription);

        // Get the zoneDescription
        restZoneDescriptionMockMvc.perform(get("/api/zone-descriptions/{id}", zoneDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zoneDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZoneDescription() throws Exception {
        // Get the zoneDescription
        restZoneDescriptionMockMvc.perform(get("/api/zone-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZoneDescription() throws Exception {
        // Initialize the database
        zoneDescriptionService.save(zoneDescription);

        int databaseSizeBeforeUpdate = zoneDescriptionRepository.findAll().size();

        // Update the zoneDescription
        ZoneDescription updatedZoneDescription = zoneDescriptionRepository.findOne(zoneDescription.getId());
        updatedZoneDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restZoneDescriptionMockMvc.perform(put("/api/zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZoneDescription)))
            .andExpect(status().isOk());

        // Validate the ZoneDescription in the database
        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ZoneDescription testZoneDescription = zoneDescriptionList.get(zoneDescriptionList.size() - 1);
        assertThat(testZoneDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testZoneDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testZoneDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ZoneDescription in Elasticsearch
        ZoneDescription zoneDescriptionEs = zoneDescriptionSearchRepository.findOne(testZoneDescription.getId());
        assertThat(zoneDescriptionEs).isEqualToComparingFieldByField(testZoneDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingZoneDescription() throws Exception {
        int databaseSizeBeforeUpdate = zoneDescriptionRepository.findAll().size();

        // Create the ZoneDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZoneDescriptionMockMvc.perform(put("/api/zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zoneDescription)))
            .andExpect(status().isCreated());

        // Validate the ZoneDescription in the database
        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZoneDescription() throws Exception {
        // Initialize the database
        zoneDescriptionService.save(zoneDescription);

        int databaseSizeBeforeDelete = zoneDescriptionRepository.findAll().size();

        // Get the zoneDescription
        restZoneDescriptionMockMvc.perform(delete("/api/zone-descriptions/{id}", zoneDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean zoneDescriptionExistsInEs = zoneDescriptionSearchRepository.exists(zoneDescription.getId());
        assertThat(zoneDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ZoneDescription> zoneDescriptionList = zoneDescriptionRepository.findAll();
        assertThat(zoneDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchZoneDescription() throws Exception {
        // Initialize the database
        zoneDescriptionService.save(zoneDescription);

        // Search the zoneDescription
        restZoneDescriptionMockMvc.perform(get("/api/_search/zone-descriptions?query=id:" + zoneDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zoneDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZoneDescription.class);
    }
}
