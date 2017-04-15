package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.repository.ShippingOriginRepository;
import com.smartshop.eshop.service.ShippingOriginService;
import com.smartshop.eshop.repository.search.ShippingOriginSearchRepository;
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
 * Test class for the ShippingOriginResource REST controller.
 *
 * @see ShippingOriginResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ShippingOriginControllerIntTest {

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private ShippingOriginRepository shippingOriginRepository;

    @Autowired
    private ShippingOriginService shippingOriginService;

    @Autowired
    private ShippingOriginSearchRepository shippingOriginSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShippingOriginMockMvc;

    private ShippingOrigin shippingOrigin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShippingOriginController shippingOriginController = new ShippingOriginController(shippingOriginService);
        this.restShippingOriginMockMvc = MockMvcBuilders.standaloneSetup(shippingOriginController)
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
    public static ShippingOrigin createEntity(EntityManager em) {
        ShippingOrigin shippingOrigin = new ShippingOrigin()
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .address(DEFAULT_ADDRESS)
            .active(DEFAULT_ACTIVE)
            .state(DEFAULT_STATE);
        return shippingOrigin;
    }

    @Before
    public void initTest() {
        shippingOriginSearchRepository.deleteAll();
        shippingOrigin = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingOrigin() throws Exception {
        int databaseSizeBeforeCreate = shippingOriginRepository.findAll().size();

        // Create the ShippingOrigin
        restShippingOriginMockMvc.perform(post("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isCreated());

        // Validate the ShippingOrigin in the database
        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingOrigin testShippingOrigin = shippingOriginList.get(shippingOriginList.size() - 1);
        assertThat(testShippingOrigin.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShippingOrigin.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testShippingOrigin.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShippingOrigin.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testShippingOrigin.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the ShippingOrigin in Elasticsearch
        ShippingOrigin shippingOriginEs = shippingOriginSearchRepository.findOne(testShippingOrigin.getId());
        assertThat(shippingOriginEs).isEqualToComparingFieldByField(testShippingOrigin);
    }

    @Test
    @Transactional
    public void createShippingOriginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingOriginRepository.findAll().size();

        // Create the ShippingOrigin with an existing ID
        shippingOrigin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingOriginMockMvc.perform(post("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingOriginRepository.findAll().size();
        // set the field null
        shippingOrigin.setCity(null);

        // Create the ShippingOrigin, which fails.

        restShippingOriginMockMvc.perform(post("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isBadRequest());

        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingOriginRepository.findAll().size();
        // set the field null
        shippingOrigin.setPostalCode(null);

        // Create the ShippingOrigin, which fails.

        restShippingOriginMockMvc.perform(post("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isBadRequest());

        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = shippingOriginRepository.findAll().size();
        // set the field null
        shippingOrigin.setAddress(null);

        // Create the ShippingOrigin, which fails.

        restShippingOriginMockMvc.perform(post("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isBadRequest());

        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShippingOrigins() throws Exception {
        // Initialize the database
        shippingOriginRepository.saveAndFlush(shippingOrigin);

        // Get all the shippingOriginList
        restShippingOriginMockMvc.perform(get("/api/shipping-origins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingOrigin.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getShippingOrigin() throws Exception {
        // Initialize the database
        shippingOriginRepository.saveAndFlush(shippingOrigin);

        // Get the shippingOrigin
        restShippingOriginMockMvc.perform(get("/api/shipping-origins/{id}", shippingOrigin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingOrigin.getId().intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingOrigin() throws Exception {
        // Get the shippingOrigin
        restShippingOriginMockMvc.perform(get("/api/shipping-origins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingOrigin() throws Exception {
        // Initialize the database
        shippingOriginService.save(shippingOrigin);

        int databaseSizeBeforeUpdate = shippingOriginRepository.findAll().size();

        // Update the shippingOrigin
        ShippingOrigin updatedShippingOrigin = shippingOriginRepository.findOne(shippingOrigin.getId());
        updatedShippingOrigin
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .address(UPDATED_ADDRESS)
            .active(UPDATED_ACTIVE)
            .state(UPDATED_STATE);

        restShippingOriginMockMvc.perform(put("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShippingOrigin)))
            .andExpect(status().isOk());

        // Validate the ShippingOrigin in the database
        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeUpdate);
        ShippingOrigin testShippingOrigin = shippingOriginList.get(shippingOriginList.size() - 1);
        assertThat(testShippingOrigin.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShippingOrigin.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShippingOrigin.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShippingOrigin.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testShippingOrigin.getState()).isEqualTo(UPDATED_STATE);

        // Validate the ShippingOrigin in Elasticsearch
        ShippingOrigin shippingOriginEs = shippingOriginSearchRepository.findOne(testShippingOrigin.getId());
        assertThat(shippingOriginEs).isEqualToComparingFieldByField(testShippingOrigin);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingOrigin() throws Exception {
        int databaseSizeBeforeUpdate = shippingOriginRepository.findAll().size();

        // Create the ShippingOrigin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShippingOriginMockMvc.perform(put("/api/shipping-origins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrigin)))
            .andExpect(status().isCreated());

        // Validate the ShippingOrigin in the database
        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShippingOrigin() throws Exception {
        // Initialize the database
        shippingOriginService.save(shippingOrigin);

        int databaseSizeBeforeDelete = shippingOriginRepository.findAll().size();

        // Get the shippingOrigin
        restShippingOriginMockMvc.perform(delete("/api/shipping-origins/{id}", shippingOrigin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shippingOriginExistsInEs = shippingOriginSearchRepository.exists(shippingOrigin.getId());
        assertThat(shippingOriginExistsInEs).isFalse();

        // Validate the database is empty
        List<ShippingOrigin> shippingOriginList = shippingOriginRepository.findAll();
        assertThat(shippingOriginList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShippingOrigin() throws Exception {
        // Initialize the database
        shippingOriginService.save(shippingOrigin);

        // Search the shippingOrigin
        restShippingOriginMockMvc.perform(get("/api/_search/shipping-origins?query=id:" + shippingOrigin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingOrigin.getId().intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingOrigin.class);
    }
}
