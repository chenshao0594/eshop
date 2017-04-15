package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.GeoZoneDescription;
import com.smartshop.eshop.repository.GeoZoneDescriptionRepository;
import com.smartshop.eshop.service.GeoZoneDescriptionService;
import com.smartshop.eshop.repository.search.GeoZoneDescriptionSearchRepository;
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
 * Test class for the GeoZoneDescriptionResource REST controller.
 *
 * @see GeoZoneDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class GeoZoneDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GeoZoneDescriptionRepository geoZoneDescriptionRepository;

    @Autowired
    private GeoZoneDescriptionService geoZoneDescriptionService;

    @Autowired
    private GeoZoneDescriptionSearchRepository geoZoneDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeoZoneDescriptionMockMvc;

    private GeoZoneDescription geoZoneDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeoZoneDescriptionController geoZoneDescriptionController = new GeoZoneDescriptionController(geoZoneDescriptionService);
        this.restGeoZoneDescriptionMockMvc = MockMvcBuilders.standaloneSetup(geoZoneDescriptionController)
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
    public static GeoZoneDescription createEntity(EntityManager em) {
        GeoZoneDescription geoZoneDescription = new GeoZoneDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return geoZoneDescription;
    }

    @Before
    public void initTest() {
        geoZoneDescriptionSearchRepository.deleteAll();
        geoZoneDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoZoneDescription() throws Exception {
        int databaseSizeBeforeCreate = geoZoneDescriptionRepository.findAll().size();

        // Create the GeoZoneDescription
        restGeoZoneDescriptionMockMvc.perform(post("/api/geo-zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZoneDescription)))
            .andExpect(status().isCreated());

        // Validate the GeoZoneDescription in the database
        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        GeoZoneDescription testGeoZoneDescription = geoZoneDescriptionList.get(geoZoneDescriptionList.size() - 1);
        assertThat(testGeoZoneDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGeoZoneDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeoZoneDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the GeoZoneDescription in Elasticsearch
        GeoZoneDescription geoZoneDescriptionEs = geoZoneDescriptionSearchRepository.findOne(testGeoZoneDescription.getId());
        assertThat(geoZoneDescriptionEs).isEqualToComparingFieldByField(testGeoZoneDescription);
    }

    @Test
    @Transactional
    public void createGeoZoneDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoZoneDescriptionRepository.findAll().size();

        // Create the GeoZoneDescription with an existing ID
        geoZoneDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoZoneDescriptionMockMvc.perform(post("/api/geo-zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZoneDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = geoZoneDescriptionRepository.findAll().size();
        // set the field null
        geoZoneDescription.setName(null);

        // Create the GeoZoneDescription, which fails.

        restGeoZoneDescriptionMockMvc.perform(post("/api/geo-zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZoneDescription)))
            .andExpect(status().isBadRequest());

        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeoZoneDescriptions() throws Exception {
        // Initialize the database
        geoZoneDescriptionRepository.saveAndFlush(geoZoneDescription);

        // Get all the geoZoneDescriptionList
        restGeoZoneDescriptionMockMvc.perform(get("/api/geo-zone-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoZoneDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getGeoZoneDescription() throws Exception {
        // Initialize the database
        geoZoneDescriptionRepository.saveAndFlush(geoZoneDescription);

        // Get the geoZoneDescription
        restGeoZoneDescriptionMockMvc.perform(get("/api/geo-zone-descriptions/{id}", geoZoneDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geoZoneDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeoZoneDescription() throws Exception {
        // Get the geoZoneDescription
        restGeoZoneDescriptionMockMvc.perform(get("/api/geo-zone-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoZoneDescription() throws Exception {
        // Initialize the database
        geoZoneDescriptionService.save(geoZoneDescription);

        int databaseSizeBeforeUpdate = geoZoneDescriptionRepository.findAll().size();

        // Update the geoZoneDescription
        GeoZoneDescription updatedGeoZoneDescription = geoZoneDescriptionRepository.findOne(geoZoneDescription.getId());
        updatedGeoZoneDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restGeoZoneDescriptionMockMvc.perform(put("/api/geo-zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeoZoneDescription)))
            .andExpect(status().isOk());

        // Validate the GeoZoneDescription in the database
        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeUpdate);
        GeoZoneDescription testGeoZoneDescription = geoZoneDescriptionList.get(geoZoneDescriptionList.size() - 1);
        assertThat(testGeoZoneDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGeoZoneDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeoZoneDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the GeoZoneDescription in Elasticsearch
        GeoZoneDescription geoZoneDescriptionEs = geoZoneDescriptionSearchRepository.findOne(testGeoZoneDescription.getId());
        assertThat(geoZoneDescriptionEs).isEqualToComparingFieldByField(testGeoZoneDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoZoneDescription() throws Exception {
        int databaseSizeBeforeUpdate = geoZoneDescriptionRepository.findAll().size();

        // Create the GeoZoneDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeoZoneDescriptionMockMvc.perform(put("/api/geo-zone-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZoneDescription)))
            .andExpect(status().isCreated());

        // Validate the GeoZoneDescription in the database
        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGeoZoneDescription() throws Exception {
        // Initialize the database
        geoZoneDescriptionService.save(geoZoneDescription);

        int databaseSizeBeforeDelete = geoZoneDescriptionRepository.findAll().size();

        // Get the geoZoneDescription
        restGeoZoneDescriptionMockMvc.perform(delete("/api/geo-zone-descriptions/{id}", geoZoneDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean geoZoneDescriptionExistsInEs = geoZoneDescriptionSearchRepository.exists(geoZoneDescription.getId());
        assertThat(geoZoneDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<GeoZoneDescription> geoZoneDescriptionList = geoZoneDescriptionRepository.findAll();
        assertThat(geoZoneDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGeoZoneDescription() throws Exception {
        // Initialize the database
        geoZoneDescriptionService.save(geoZoneDescription);

        // Search the geoZoneDescription
        restGeoZoneDescriptionMockMvc.perform(get("/api/_search/geo-zone-descriptions?query=id:" + geoZoneDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoZoneDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeoZoneDescription.class);
    }
}
