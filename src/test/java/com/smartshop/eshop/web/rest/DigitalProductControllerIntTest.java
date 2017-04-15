package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.DigitalProduct;
import com.smartshop.eshop.repository.DigitalProductRepository;
import com.smartshop.eshop.service.DigitalProductService;
import com.smartshop.eshop.repository.search.DigitalProductSearchRepository;
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
 * Test class for the DigitalProductResource REST controller.
 *
 * @see DigitalProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class DigitalProductControllerIntTest {

    private static final String DEFAULT_PRODUCT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_FILE_NAME = "BBBBBBBBBB";

    @Autowired
    private DigitalProductRepository digitalProductRepository;

    @Autowired
    private DigitalProductService digitalProductService;

    @Autowired
    private DigitalProductSearchRepository digitalProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDigitalProductMockMvc;

    private DigitalProduct digitalProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DigitalProductController digitalProductController = new DigitalProductController(digitalProductService);
        this.restDigitalProductMockMvc = MockMvcBuilders.standaloneSetup(digitalProductController)
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
    public static DigitalProduct createEntity(EntityManager em) {
        DigitalProduct digitalProduct = new DigitalProduct()
            .productFileName(DEFAULT_PRODUCT_FILE_NAME);
        return digitalProduct;
    }

    @Before
    public void initTest() {
        digitalProductSearchRepository.deleteAll();
        digitalProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createDigitalProduct() throws Exception {
        int databaseSizeBeforeCreate = digitalProductRepository.findAll().size();

        // Create the DigitalProduct
        restDigitalProductMockMvc.perform(post("/api/digital-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalProduct)))
            .andExpect(status().isCreated());

        // Validate the DigitalProduct in the database
        List<DigitalProduct> digitalProductList = digitalProductRepository.findAll();
        assertThat(digitalProductList).hasSize(databaseSizeBeforeCreate + 1);
        DigitalProduct testDigitalProduct = digitalProductList.get(digitalProductList.size() - 1);
        assertThat(testDigitalProduct.getProductFileName()).isEqualTo(DEFAULT_PRODUCT_FILE_NAME);

        // Validate the DigitalProduct in Elasticsearch
        DigitalProduct digitalProductEs = digitalProductSearchRepository.findOne(testDigitalProduct.getId());
        assertThat(digitalProductEs).isEqualToComparingFieldByField(testDigitalProduct);
    }

    @Test
    @Transactional
    public void createDigitalProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = digitalProductRepository.findAll().size();

        // Create the DigitalProduct with an existing ID
        digitalProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDigitalProductMockMvc.perform(post("/api/digital-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalProduct)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DigitalProduct> digitalProductList = digitalProductRepository.findAll();
        assertThat(digitalProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDigitalProducts() throws Exception {
        // Initialize the database
        digitalProductRepository.saveAndFlush(digitalProduct);

        // Get all the digitalProductList
        restDigitalProductMockMvc.perform(get("/api/digital-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(digitalProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productFileName").value(hasItem(DEFAULT_PRODUCT_FILE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDigitalProduct() throws Exception {
        // Initialize the database
        digitalProductRepository.saveAndFlush(digitalProduct);

        // Get the digitalProduct
        restDigitalProductMockMvc.perform(get("/api/digital-products/{id}", digitalProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(digitalProduct.getId().intValue()))
            .andExpect(jsonPath("$.productFileName").value(DEFAULT_PRODUCT_FILE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDigitalProduct() throws Exception {
        // Get the digitalProduct
        restDigitalProductMockMvc.perform(get("/api/digital-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDigitalProduct() throws Exception {
        // Initialize the database
        digitalProductService.save(digitalProduct);

        int databaseSizeBeforeUpdate = digitalProductRepository.findAll().size();

        // Update the digitalProduct
        DigitalProduct updatedDigitalProduct = digitalProductRepository.findOne(digitalProduct.getId());
        updatedDigitalProduct
            .productFileName(UPDATED_PRODUCT_FILE_NAME);

        restDigitalProductMockMvc.perform(put("/api/digital-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDigitalProduct)))
            .andExpect(status().isOk());

        // Validate the DigitalProduct in the database
        List<DigitalProduct> digitalProductList = digitalProductRepository.findAll();
        assertThat(digitalProductList).hasSize(databaseSizeBeforeUpdate);
        DigitalProduct testDigitalProduct = digitalProductList.get(digitalProductList.size() - 1);
        assertThat(testDigitalProduct.getProductFileName()).isEqualTo(UPDATED_PRODUCT_FILE_NAME);

        // Validate the DigitalProduct in Elasticsearch
        DigitalProduct digitalProductEs = digitalProductSearchRepository.findOne(testDigitalProduct.getId());
        assertThat(digitalProductEs).isEqualToComparingFieldByField(testDigitalProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingDigitalProduct() throws Exception {
        int databaseSizeBeforeUpdate = digitalProductRepository.findAll().size();

        // Create the DigitalProduct

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDigitalProductMockMvc.perform(put("/api/digital-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(digitalProduct)))
            .andExpect(status().isCreated());

        // Validate the DigitalProduct in the database
        List<DigitalProduct> digitalProductList = digitalProductRepository.findAll();
        assertThat(digitalProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDigitalProduct() throws Exception {
        // Initialize the database
        digitalProductService.save(digitalProduct);

        int databaseSizeBeforeDelete = digitalProductRepository.findAll().size();

        // Get the digitalProduct
        restDigitalProductMockMvc.perform(delete("/api/digital-products/{id}", digitalProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean digitalProductExistsInEs = digitalProductSearchRepository.exists(digitalProduct.getId());
        assertThat(digitalProductExistsInEs).isFalse();

        // Validate the database is empty
        List<DigitalProduct> digitalProductList = digitalProductRepository.findAll();
        assertThat(digitalProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDigitalProduct() throws Exception {
        // Initialize the database
        digitalProductService.save(digitalProduct);

        // Search the digitalProduct
        restDigitalProductMockMvc.perform(get("/api/_search/digital-products?query=id:" + digitalProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(digitalProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productFileName").value(hasItem(DEFAULT_PRODUCT_FILE_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DigitalProduct.class);
    }
}
