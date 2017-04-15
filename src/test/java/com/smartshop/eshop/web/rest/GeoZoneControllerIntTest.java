package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.GeoZone;
import com.smartshop.eshop.repository.GeoZoneRepository;
import com.smartshop.eshop.service.GeoZoneService;
import com.smartshop.eshop.repository.search.GeoZoneSearchRepository;
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
 * Test class for the GeoZoneResource REST controller.
 *
 * @see GeoZoneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class GeoZoneControllerIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private GeoZoneRepository geoZoneRepository;

    @Autowired
    private GeoZoneService geoZoneService;

    @Autowired
    private GeoZoneSearchRepository geoZoneSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeoZoneMockMvc;

    private GeoZone geoZone;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GeoZoneController geoZoneController = new GeoZoneController(geoZoneService);
        this.restGeoZoneMockMvc = MockMvcBuilders.standaloneSetup(geoZoneController)
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
    public static GeoZone createEntity(EntityManager em) {
        GeoZone geoZone = new GeoZone()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE);
        return geoZone;
    }

    @Before
    public void initTest() {
        geoZoneSearchRepository.deleteAll();
        geoZone = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoZone() throws Exception {
        int databaseSizeBeforeCreate = geoZoneRepository.findAll().size();

        // Create the GeoZone
        restGeoZoneMockMvc.perform(post("/api/geo-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZone)))
            .andExpect(status().isCreated());

        // Validate the GeoZone in the database
        List<GeoZone> geoZoneList = geoZoneRepository.findAll();
        assertThat(geoZoneList).hasSize(databaseSizeBeforeCreate + 1);
        GeoZone testGeoZone = geoZoneList.get(geoZoneList.size() - 1);
        assertThat(testGeoZone.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeoZone.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the GeoZone in Elasticsearch
        GeoZone geoZoneEs = geoZoneSearchRepository.findOne(testGeoZone.getId());
        assertThat(geoZoneEs).isEqualToComparingFieldByField(testGeoZone);
    }

    @Test
    @Transactional
    public void createGeoZoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoZoneRepository.findAll().size();

        // Create the GeoZone with an existing ID
        geoZone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoZoneMockMvc.perform(post("/api/geo-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZone)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<GeoZone> geoZoneList = geoZoneRepository.findAll();
        assertThat(geoZoneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeoZones() throws Exception {
        // Initialize the database
        geoZoneRepository.saveAndFlush(geoZone);

        // Get all the geoZoneList
        restGeoZoneMockMvc.perform(get("/api/geo-zones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getGeoZone() throws Exception {
        // Initialize the database
        geoZoneRepository.saveAndFlush(geoZone);

        // Get the geoZone
        restGeoZoneMockMvc.perform(get("/api/geo-zones/{id}", geoZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geoZone.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeoZone() throws Exception {
        // Get the geoZone
        restGeoZoneMockMvc.perform(get("/api/geo-zones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoZone() throws Exception {
        // Initialize the database
        geoZoneService.save(geoZone);

        int databaseSizeBeforeUpdate = geoZoneRepository.findAll().size();

        // Update the geoZone
        GeoZone updatedGeoZone = geoZoneRepository.findOne(geoZone.getId());
        updatedGeoZone
            .name(UPDATED_NAME)
            .code(UPDATED_CODE);

        restGeoZoneMockMvc.perform(put("/api/geo-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeoZone)))
            .andExpect(status().isOk());

        // Validate the GeoZone in the database
        List<GeoZone> geoZoneList = geoZoneRepository.findAll();
        assertThat(geoZoneList).hasSize(databaseSizeBeforeUpdate);
        GeoZone testGeoZone = geoZoneList.get(geoZoneList.size() - 1);
        assertThat(testGeoZone.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeoZone.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the GeoZone in Elasticsearch
        GeoZone geoZoneEs = geoZoneSearchRepository.findOne(testGeoZone.getId());
        assertThat(geoZoneEs).isEqualToComparingFieldByField(testGeoZone);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoZone() throws Exception {
        int databaseSizeBeforeUpdate = geoZoneRepository.findAll().size();

        // Create the GeoZone

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeoZoneMockMvc.perform(put("/api/geo-zones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoZone)))
            .andExpect(status().isCreated());

        // Validate the GeoZone in the database
        List<GeoZone> geoZoneList = geoZoneRepository.findAll();
        assertThat(geoZoneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGeoZone() throws Exception {
        // Initialize the database
        geoZoneService.save(geoZone);

        int databaseSizeBeforeDelete = geoZoneRepository.findAll().size();

        // Get the geoZone
        restGeoZoneMockMvc.perform(delete("/api/geo-zones/{id}", geoZone.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean geoZoneExistsInEs = geoZoneSearchRepository.exists(geoZone.getId());
        assertThat(geoZoneExistsInEs).isFalse();

        // Validate the database is empty
        List<GeoZone> geoZoneList = geoZoneRepository.findAll();
        assertThat(geoZoneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGeoZone() throws Exception {
        // Initialize the database
        geoZoneService.save(geoZone);

        // Search the geoZone
        restGeoZoneMockMvc.perform(get("/api/_search/geo-zones?query=id:" + geoZone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoZone.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeoZone.class);
    }
}
