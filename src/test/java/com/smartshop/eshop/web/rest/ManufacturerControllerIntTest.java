package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Manufacturer;
import com.smartshop.eshop.repository.ManufacturerRepository;
import com.smartshop.eshop.service.ManufacturerService;
import com.smartshop.eshop.repository.search.ManufacturerSearchRepository;
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
 * Test class for the ManufacturerResource REST controller.
 *
 * @see ManufacturerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ManufacturerControllerIntTest {

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ManufacturerSearchRepository manufacturerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restManufacturerMockMvc;

    private Manufacturer manufacturer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ManufacturerController manufacturerController = new ManufacturerController(manufacturerService);
        this.restManufacturerMockMvc = MockMvcBuilders.standaloneSetup(manufacturerController)
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
    public static Manufacturer createEntity(EntityManager em) {
        Manufacturer manufacturer = new Manufacturer()
            .order(DEFAULT_ORDER)
            .image(DEFAULT_IMAGE)
            .code(DEFAULT_CODE);
        return manufacturer;
    }

    @Before
    public void initTest() {
        manufacturerSearchRepository.deleteAll();
        manufacturer = createEntity(em);
    }

    @Test
    @Transactional
    public void createManufacturer() throws Exception {
        int databaseSizeBeforeCreate = manufacturerRepository.findAll().size();

        // Create the Manufacturer
        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
            .andExpect(status().isCreated());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeCreate + 1);
        Manufacturer testManufacturer = manufacturerList.get(manufacturerList.size() - 1);
        assertThat(testManufacturer.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testManufacturer.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testManufacturer.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the Manufacturer in Elasticsearch
        Manufacturer manufacturerEs = manufacturerSearchRepository.findOne(testManufacturer.getId());
        assertThat(manufacturerEs).isEqualToComparingFieldByField(testManufacturer);
    }

    @Test
    @Transactional
    public void createManufacturerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = manufacturerRepository.findAll().size();

        // Create the Manufacturer with an existing ID
        manufacturer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = manufacturerRepository.findAll().size();
        // set the field null
        manufacturer.setCode(null);

        // Create the Manufacturer, which fails.

        restManufacturerMockMvc.perform(post("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
            .andExpect(status().isBadRequest());

        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllManufacturers() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList
        restManufacturerMockMvc.perform(get("/api/manufacturers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getManufacturer() throws Exception {
        // Initialize the database
        manufacturerRepository.saveAndFlush(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(manufacturer.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get("/api/manufacturers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateManufacturer() throws Exception {
        // Initialize the database
        manufacturerService.save(manufacturer);

        int databaseSizeBeforeUpdate = manufacturerRepository.findAll().size();

        // Update the manufacturer
        Manufacturer updatedManufacturer = manufacturerRepository.findOne(manufacturer.getId());
        updatedManufacturer
            .order(UPDATED_ORDER)
            .image(UPDATED_IMAGE)
            .code(UPDATED_CODE);

        restManufacturerMockMvc.perform(put("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedManufacturer)))
            .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeUpdate);
        Manufacturer testManufacturer = manufacturerList.get(manufacturerList.size() - 1);
        assertThat(testManufacturer.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testManufacturer.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testManufacturer.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the Manufacturer in Elasticsearch
        Manufacturer manufacturerEs = manufacturerSearchRepository.findOne(testManufacturer.getId());
        assertThat(manufacturerEs).isEqualToComparingFieldByField(testManufacturer);
    }

    @Test
    @Transactional
    public void updateNonExistingManufacturer() throws Exception {
        int databaseSizeBeforeUpdate = manufacturerRepository.findAll().size();

        // Create the Manufacturer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restManufacturerMockMvc.perform(put("/api/manufacturers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(manufacturer)))
            .andExpect(status().isCreated());

        // Validate the Manufacturer in the database
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteManufacturer() throws Exception {
        // Initialize the database
        manufacturerService.save(manufacturer);

        int databaseSizeBeforeDelete = manufacturerRepository.findAll().size();

        // Get the manufacturer
        restManufacturerMockMvc.perform(delete("/api/manufacturers/{id}", manufacturer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean manufacturerExistsInEs = manufacturerSearchRepository.exists(manufacturer.getId());
        assertThat(manufacturerExistsInEs).isFalse();

        // Validate the database is empty
        List<Manufacturer> manufacturerList = manufacturerRepository.findAll();
        assertThat(manufacturerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchManufacturer() throws Exception {
        // Initialize the database
        manufacturerService.save(manufacturer);

        // Search the manufacturer
        restManufacturerMockMvc.perform(get("/api/_search/manufacturers?query=id:" + manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manufacturer.class);
    }
}
