package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CountryDescription;
import com.smartshop.eshop.repository.CountryDescriptionRepository;
import com.smartshop.eshop.service.CountryDescriptionService;
import com.smartshop.eshop.repository.search.CountryDescriptionSearchRepository;
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
 * Test class for the CountryDescriptionResource REST controller.
 *
 * @see CountryDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CountryDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CountryDescriptionRepository countryDescriptionRepository;

    @Autowired
    private CountryDescriptionService countryDescriptionService;

    @Autowired
    private CountryDescriptionSearchRepository countryDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCountryDescriptionMockMvc;

    private CountryDescription countryDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountryDescriptionController countryDescriptionController = new CountryDescriptionController(countryDescriptionService);
        this.restCountryDescriptionMockMvc = MockMvcBuilders.standaloneSetup(countryDescriptionController)
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
    public static CountryDescription createEntity(EntityManager em) {
        CountryDescription countryDescription = new CountryDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return countryDescription;
    }

    @Before
    public void initTest() {
        countryDescriptionSearchRepository.deleteAll();
        countryDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCountryDescription() throws Exception {
        int databaseSizeBeforeCreate = countryDescriptionRepository.findAll().size();

        // Create the CountryDescription
        restCountryDescriptionMockMvc.perform(post("/api/country-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDescription)))
            .andExpect(status().isCreated());

        // Validate the CountryDescription in the database
        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CountryDescription testCountryDescription = countryDescriptionList.get(countryDescriptionList.size() - 1);
        assertThat(testCountryDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCountryDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCountryDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the CountryDescription in Elasticsearch
        CountryDescription countryDescriptionEs = countryDescriptionSearchRepository.findOne(testCountryDescription.getId());
        assertThat(countryDescriptionEs).isEqualToComparingFieldByField(testCountryDescription);
    }

    @Test
    @Transactional
    public void createCountryDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = countryDescriptionRepository.findAll().size();

        // Create the CountryDescription with an existing ID
        countryDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountryDescriptionMockMvc.perform(post("/api/country-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = countryDescriptionRepository.findAll().size();
        // set the field null
        countryDescription.setName(null);

        // Create the CountryDescription, which fails.

        restCountryDescriptionMockMvc.perform(post("/api/country-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDescription)))
            .andExpect(status().isBadRequest());

        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCountryDescriptions() throws Exception {
        // Initialize the database
        countryDescriptionRepository.saveAndFlush(countryDescription);

        // Get all the countryDescriptionList
        restCountryDescriptionMockMvc.perform(get("/api/country-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCountryDescription() throws Exception {
        // Initialize the database
        countryDescriptionRepository.saveAndFlush(countryDescription);

        // Get the countryDescription
        restCountryDescriptionMockMvc.perform(get("/api/country-descriptions/{id}", countryDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(countryDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountryDescription() throws Exception {
        // Get the countryDescription
        restCountryDescriptionMockMvc.perform(get("/api/country-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryDescription() throws Exception {
        // Initialize the database
        countryDescriptionService.save(countryDescription);

        int databaseSizeBeforeUpdate = countryDescriptionRepository.findAll().size();

        // Update the countryDescription
        CountryDescription updatedCountryDescription = countryDescriptionRepository.findOne(countryDescription.getId());
        updatedCountryDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCountryDescriptionMockMvc.perform(put("/api/country-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCountryDescription)))
            .andExpect(status().isOk());

        // Validate the CountryDescription in the database
        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeUpdate);
        CountryDescription testCountryDescription = countryDescriptionList.get(countryDescriptionList.size() - 1);
        assertThat(testCountryDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCountryDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCountryDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the CountryDescription in Elasticsearch
        CountryDescription countryDescriptionEs = countryDescriptionSearchRepository.findOne(testCountryDescription.getId());
        assertThat(countryDescriptionEs).isEqualToComparingFieldByField(testCountryDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingCountryDescription() throws Exception {
        int databaseSizeBeforeUpdate = countryDescriptionRepository.findAll().size();

        // Create the CountryDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCountryDescriptionMockMvc.perform(put("/api/country-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(countryDescription)))
            .andExpect(status().isCreated());

        // Validate the CountryDescription in the database
        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCountryDescription() throws Exception {
        // Initialize the database
        countryDescriptionService.save(countryDescription);

        int databaseSizeBeforeDelete = countryDescriptionRepository.findAll().size();

        // Get the countryDescription
        restCountryDescriptionMockMvc.perform(delete("/api/country-descriptions/{id}", countryDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean countryDescriptionExistsInEs = countryDescriptionSearchRepository.exists(countryDescription.getId());
        assertThat(countryDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<CountryDescription> countryDescriptionList = countryDescriptionRepository.findAll();
        assertThat(countryDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCountryDescription() throws Exception {
        // Initialize the database
        countryDescriptionService.save(countryDescription);

        // Search the countryDescription
        restCountryDescriptionMockMvc.perform(get("/api/_search/country-descriptions?query=id:" + countryDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countryDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountryDescription.class);
    }
}
