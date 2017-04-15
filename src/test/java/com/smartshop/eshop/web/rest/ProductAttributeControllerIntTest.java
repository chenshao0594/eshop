package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.repository.ProductAttributeRepository;
import com.smartshop.eshop.service.ProductAttributeService;
import com.smartshop.eshop.repository.search.ProductAttributeSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductAttributeResource REST controller.
 *
 * @see ProductAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductAttributeControllerIntTest {

    private static final BigDecimal DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_ATTRIBUTE_WEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRODUCT_ATTRIBUTE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_ATTRIBUTE_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_ATTRIBUTE_REQUIRED = false;
    private static final Boolean UPDATED_ATTRIBUTE_REQUIRED = true;

    private static final Boolean DEFAULT_ATTRIBUTE_DEFAULT = false;
    private static final Boolean UPDATED_ATTRIBUTE_DEFAULT = true;

    private static final Boolean DEFAULT_ATTRIBUTE_DISPLAY_ONLY = false;
    private static final Boolean UPDATED_ATTRIBUTE_DISPLAY_ONLY = true;

    private static final Integer DEFAULT_PRODUCT_OPTION_SORT_ORDER = 1;
    private static final Integer UPDATED_PRODUCT_OPTION_SORT_ORDER = 2;

    private static final Boolean DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE = false;
    private static final Boolean UPDATED_PRODUCT_ATTRIBUTE_IS_FREE = true;

    private static final Boolean DEFAULT_ATTRIBUTE_DISCOUNTED = false;
    private static final Boolean UPDATED_ATTRIBUTE_DISCOUNTED = true;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private ProductAttributeSearchRepository productAttributeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductAttributeMockMvc;

    private ProductAttribute productAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductAttributeController productAttributeController = new ProductAttributeController(productAttributeService);
        this.restProductAttributeMockMvc = MockMvcBuilders.standaloneSetup(productAttributeController)
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
    public static ProductAttribute createEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .productAttributeWeight(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT)
            .productAttributePrice(DEFAULT_PRODUCT_ATTRIBUTE_PRICE)
            .attributeRequired(DEFAULT_ATTRIBUTE_REQUIRED)
            .attributeDefault(DEFAULT_ATTRIBUTE_DEFAULT)
            .attributeDisplayOnly(DEFAULT_ATTRIBUTE_DISPLAY_ONLY)
            .productOptionSortOrder(DEFAULT_PRODUCT_OPTION_SORT_ORDER)
            .productAttributeIsFree(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE)
            .attributeDiscounted(DEFAULT_ATTRIBUTE_DISCOUNTED);
        return productAttribute;
    }

    @Before
    public void initTest() {
        productAttributeSearchRepository.deleteAll();
        productAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductAttributeWeight()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT);
        assertThat(testProductAttribute.getProductAttributePrice()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_PRICE);
        assertThat(testProductAttribute.isAttributeRequired()).isEqualTo(DEFAULT_ATTRIBUTE_REQUIRED);
        assertThat(testProductAttribute.isAttributeDefault()).isEqualTo(DEFAULT_ATTRIBUTE_DEFAULT);
        assertThat(testProductAttribute.isAttributeDisplayOnly()).isEqualTo(DEFAULT_ATTRIBUTE_DISPLAY_ONLY);
        assertThat(testProductAttribute.getProductOptionSortOrder()).isEqualTo(DEFAULT_PRODUCT_OPTION_SORT_ORDER);
        assertThat(testProductAttribute.isProductAttributeIsFree()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE);
        assertThat(testProductAttribute.isAttributeDiscounted()).isEqualTo(DEFAULT_ATTRIBUTE_DISCOUNTED);

        // Validate the ProductAttribute in Elasticsearch
        ProductAttribute productAttributeEs = productAttributeSearchRepository.findOne(testProductAttribute.getId());
        assertThat(productAttributeEs).isEqualToComparingFieldByField(testProductAttribute);
    }

    @Test
    @Transactional
    public void createProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute with an existing ID
        productAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductAttributes() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeWeight").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productAttributePrice").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].attributeRequired").value(hasItem(DEFAULT_ATTRIBUTE_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDefault").value(hasItem(DEFAULT_ATTRIBUTE_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDisplayOnly").value(hasItem(DEFAULT_ATTRIBUTE_DISPLAY_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productAttributeIsFree").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDiscounted").value(hasItem(DEFAULT_ATTRIBUTE_DISCOUNTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttribute.getId().intValue()))
            .andExpect(jsonPath("$.productAttributeWeight").value(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue()))
            .andExpect(jsonPath("$.productAttributePrice").value(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue()))
            .andExpect(jsonPath("$.attributeRequired").value(DEFAULT_ATTRIBUTE_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.attributeDefault").value(DEFAULT_ATTRIBUTE_DEFAULT.booleanValue()))
            .andExpect(jsonPath("$.attributeDisplayOnly").value(DEFAULT_ATTRIBUTE_DISPLAY_ONLY.booleanValue()))
            .andExpect(jsonPath("$.productOptionSortOrder").value(DEFAULT_PRODUCT_OPTION_SORT_ORDER))
            .andExpect(jsonPath("$.productAttributeIsFree").value(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue()))
            .andExpect(jsonPath("$.attributeDiscounted").value(DEFAULT_ATTRIBUTE_DISCOUNTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductAttribute() throws Exception {
        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttribute() throws Exception {
        // Initialize the database
        productAttributeService.save(productAttribute);

        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Update the productAttribute
        ProductAttribute updatedProductAttribute = productAttributeRepository.findOne(productAttribute.getId());
        updatedProductAttribute
            .productAttributeWeight(UPDATED_PRODUCT_ATTRIBUTE_WEIGHT)
            .productAttributePrice(UPDATED_PRODUCT_ATTRIBUTE_PRICE)
            .attributeRequired(UPDATED_ATTRIBUTE_REQUIRED)
            .attributeDefault(UPDATED_ATTRIBUTE_DEFAULT)
            .attributeDisplayOnly(UPDATED_ATTRIBUTE_DISPLAY_ONLY)
            .productOptionSortOrder(UPDATED_PRODUCT_OPTION_SORT_ORDER)
            .productAttributeIsFree(UPDATED_PRODUCT_ATTRIBUTE_IS_FREE)
            .attributeDiscounted(UPDATED_ATTRIBUTE_DISCOUNTED);

        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAttribute)))
            .andExpect(status().isOk());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductAttributeWeight()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_WEIGHT);
        assertThat(testProductAttribute.getProductAttributePrice()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_PRICE);
        assertThat(testProductAttribute.isAttributeRequired()).isEqualTo(UPDATED_ATTRIBUTE_REQUIRED);
        assertThat(testProductAttribute.isAttributeDefault()).isEqualTo(UPDATED_ATTRIBUTE_DEFAULT);
        assertThat(testProductAttribute.isAttributeDisplayOnly()).isEqualTo(UPDATED_ATTRIBUTE_DISPLAY_ONLY);
        assertThat(testProductAttribute.getProductOptionSortOrder()).isEqualTo(UPDATED_PRODUCT_OPTION_SORT_ORDER);
        assertThat(testProductAttribute.isProductAttributeIsFree()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_IS_FREE);
        assertThat(testProductAttribute.isAttributeDiscounted()).isEqualTo(UPDATED_ATTRIBUTE_DISCOUNTED);

        // Validate the ProductAttribute in Elasticsearch
        ProductAttribute productAttributeEs = productAttributeSearchRepository.findOne(testProductAttribute.getId());
        assertThat(productAttributeEs).isEqualToComparingFieldByField(testProductAttribute);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttribute)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductAttribute() throws Exception {
        // Initialize the database
        productAttributeService.save(productAttribute);

        int databaseSizeBeforeDelete = productAttributeRepository.findAll().size();

        // Get the productAttribute
        restProductAttributeMockMvc.perform(delete("/api/product-attributes/{id}", productAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productAttributeExistsInEs = productAttributeSearchRepository.exists(productAttribute.getId());
        assertThat(productAttributeExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductAttribute() throws Exception {
        // Initialize the database
        productAttributeService.save(productAttribute);

        // Search the productAttribute
        restProductAttributeMockMvc.perform(get("/api/_search/product-attributes?query=id:" + productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeWeight").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productAttributePrice").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].attributeRequired").value(hasItem(DEFAULT_ATTRIBUTE_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDefault").value(hasItem(DEFAULT_ATTRIBUTE_DEFAULT.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDisplayOnly").value(hasItem(DEFAULT_ATTRIBUTE_DISPLAY_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productAttributeIsFree").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].attributeDiscounted").value(hasItem(DEFAULT_ATTRIBUTE_DISCOUNTED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttribute.class);
    }
}
