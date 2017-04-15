package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.repository.ProductRelationshipRepository;
import com.smartshop.eshop.service.ProductRelationshipService;
import com.smartshop.eshop.repository.search.ProductRelationshipSearchRepository;
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
 * Test class for the ProductRelationshipResource REST controller.
 *
 * @see ProductRelationshipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductRelationshipControllerIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ProductRelationshipRepository productRelationshipRepository;

    @Autowired
    private ProductRelationshipService productRelationshipService;

    @Autowired
    private ProductRelationshipSearchRepository productRelationshipSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductRelationshipMockMvc;

    private ProductRelationship productRelationship;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductRelationshipController productRelationshipController = new ProductRelationshipController(productRelationshipService);
        this.restProductRelationshipMockMvc = MockMvcBuilders.standaloneSetup(productRelationshipController)
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
    public static ProductRelationship createEntity(EntityManager em) {
        ProductRelationship productRelationship = new ProductRelationship()
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE);
        return productRelationship;
    }

    @Before
    public void initTest() {
        productRelationshipSearchRepository.deleteAll();
        productRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductRelationship() throws Exception {
        int databaseSizeBeforeCreate = productRelationshipRepository.findAll().size();

        // Create the ProductRelationship
        restProductRelationshipMockMvc.perform(post("/api/product-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productRelationship)))
            .andExpect(status().isCreated());

        // Validate the ProductRelationship in the database
        List<ProductRelationship> productRelationshipList = productRelationshipRepository.findAll();
        assertThat(productRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        ProductRelationship testProductRelationship = productRelationshipList.get(productRelationshipList.size() - 1);
        assertThat(testProductRelationship.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductRelationship.isActive()).isEqualTo(DEFAULT_ACTIVE);

        // Validate the ProductRelationship in Elasticsearch
        ProductRelationship productRelationshipEs = productRelationshipSearchRepository.findOne(testProductRelationship.getId());
        assertThat(productRelationshipEs).isEqualToComparingFieldByField(testProductRelationship);
    }

    @Test
    @Transactional
    public void createProductRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRelationshipRepository.findAll().size();

        // Create the ProductRelationship with an existing ID
        productRelationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductRelationshipMockMvc.perform(post("/api/product-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductRelationship> productRelationshipList = productRelationshipRepository.findAll();
        assertThat(productRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductRelationships() throws Exception {
        // Initialize the database
        productRelationshipRepository.saveAndFlush(productRelationship);

        // Get all the productRelationshipList
        restProductRelationshipMockMvc.perform(get("/api/product-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getProductRelationship() throws Exception {
        // Initialize the database
        productRelationshipRepository.saveAndFlush(productRelationship);

        // Get the productRelationship
        restProductRelationshipMockMvc.perform(get("/api/product-relationships/{id}", productRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productRelationship.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductRelationship() throws Exception {
        // Get the productRelationship
        restProductRelationshipMockMvc.perform(get("/api/product-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductRelationship() throws Exception {
        // Initialize the database
        productRelationshipService.save(productRelationship);

        int databaseSizeBeforeUpdate = productRelationshipRepository.findAll().size();

        // Update the productRelationship
        ProductRelationship updatedProductRelationship = productRelationshipRepository.findOne(productRelationship.getId());
        updatedProductRelationship
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE);

        restProductRelationshipMockMvc.perform(put("/api/product-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductRelationship)))
            .andExpect(status().isOk());

        // Validate the ProductRelationship in the database
        List<ProductRelationship> productRelationshipList = productRelationshipRepository.findAll();
        assertThat(productRelationshipList).hasSize(databaseSizeBeforeUpdate);
        ProductRelationship testProductRelationship = productRelationshipList.get(productRelationshipList.size() - 1);
        assertThat(testProductRelationship.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductRelationship.isActive()).isEqualTo(UPDATED_ACTIVE);

        // Validate the ProductRelationship in Elasticsearch
        ProductRelationship productRelationshipEs = productRelationshipSearchRepository.findOne(testProductRelationship.getId());
        assertThat(productRelationshipEs).isEqualToComparingFieldByField(testProductRelationship);
    }

    @Test
    @Transactional
    public void updateNonExistingProductRelationship() throws Exception {
        int databaseSizeBeforeUpdate = productRelationshipRepository.findAll().size();

        // Create the ProductRelationship

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductRelationshipMockMvc.perform(put("/api/product-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productRelationship)))
            .andExpect(status().isCreated());

        // Validate the ProductRelationship in the database
        List<ProductRelationship> productRelationshipList = productRelationshipRepository.findAll();
        assertThat(productRelationshipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductRelationship() throws Exception {
        // Initialize the database
        productRelationshipService.save(productRelationship);

        int databaseSizeBeforeDelete = productRelationshipRepository.findAll().size();

        // Get the productRelationship
        restProductRelationshipMockMvc.perform(delete("/api/product-relationships/{id}", productRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productRelationshipExistsInEs = productRelationshipSearchRepository.exists(productRelationship.getId());
        assertThat(productRelationshipExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductRelationship> productRelationshipList = productRelationshipRepository.findAll();
        assertThat(productRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductRelationship() throws Exception {
        // Initialize the database
        productRelationshipService.save(productRelationship);

        // Search the productRelationship
        restProductRelationshipMockMvc.perform(get("/api/_search/product-relationships?query=id:" + productRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductRelationship.class);
    }
}
