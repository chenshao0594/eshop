package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductOptionValue;
import com.smartshop.eshop.repository.ProductOptionValueRepository;
import com.smartshop.eshop.service.ProductOptionValueService;
import com.smartshop.eshop.repository.search.ProductOptionValueSearchRepository;
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
 * Test class for the ProductOptionValueResource REST controller.
 *
 * @see ProductOptionValueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductOptionValueControllerIntTest {

    private static final String DEFAULT_PRODUCT_OPTION_VALUE_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_VALUE_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER = 1;
    private static final Integer UPDATED_PRODUCT_OPTION_VALUE_SORT_ORDER = 2;

    private static final Boolean DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY = false;
    private static final Boolean UPDATED_PRODUCT_OPTION_DISPLAY_ONLY = true;

    @Autowired
    private ProductOptionValueRepository productOptionValueRepository;

    @Autowired
    private ProductOptionValueService productOptionValueService;

    @Autowired
    private ProductOptionValueSearchRepository productOptionValueSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductOptionValueMockMvc;

    private ProductOptionValue productOptionValue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductOptionValueController productOptionValueController = new ProductOptionValueController(productOptionValueService);
        this.restProductOptionValueMockMvc = MockMvcBuilders.standaloneSetup(productOptionValueController)
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
    public static ProductOptionValue createEntity(EntityManager em) {
        ProductOptionValue productOptionValue = new ProductOptionValue()
            .productOptionValueImage(DEFAULT_PRODUCT_OPTION_VALUE_IMAGE)
            .code(DEFAULT_CODE)
            .productOptionValueSortOrder(DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER)
            .productOptionDisplayOnly(DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY);
        return productOptionValue;
    }

    @Before
    public void initTest() {
        productOptionValueSearchRepository.deleteAll();
        productOptionValue = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOptionValue() throws Exception {
        int databaseSizeBeforeCreate = productOptionValueRepository.findAll().size();

        // Create the ProductOptionValue
        restProductOptionValueMockMvc.perform(post("/api/product-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValue)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionValue in the database
        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionValue testProductOptionValue = productOptionValueList.get(productOptionValueList.size() - 1);
        assertThat(testProductOptionValue.getProductOptionValueImage()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE_IMAGE);
        assertThat(testProductOptionValue.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductOptionValue.getProductOptionValueSortOrder()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER);
        assertThat(testProductOptionValue.isProductOptionDisplayOnly()).isEqualTo(DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY);

        // Validate the ProductOptionValue in Elasticsearch
        ProductOptionValue productOptionValueEs = productOptionValueSearchRepository.findOne(testProductOptionValue.getId());
        assertThat(productOptionValueEs).isEqualToComparingFieldByField(testProductOptionValue);
    }

    @Test
    @Transactional
    public void createProductOptionValueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionValueRepository.findAll().size();

        // Create the ProductOptionValue with an existing ID
        productOptionValue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionValueMockMvc.perform(post("/api/product-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValue)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionValueRepository.findAll().size();
        // set the field null
        productOptionValue.setCode(null);

        // Create the ProductOptionValue, which fails.

        restProductOptionValueMockMvc.perform(post("/api/product-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValue)))
            .andExpect(status().isBadRequest());

        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptionValues() throws Exception {
        // Initialize the database
        productOptionValueRepository.saveAndFlush(productOptionValue);

        // Get all the productOptionValueList
        restProductOptionValueMockMvc.perform(get("/api/product-option-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionValueImage").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productOptionValueSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productOptionDisplayOnly").value(hasItem(DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY.booleanValue())));
    }

    @Test
    @Transactional
    public void getProductOptionValue() throws Exception {
        // Initialize the database
        productOptionValueRepository.saveAndFlush(productOptionValue);

        // Get the productOptionValue
        restProductOptionValueMockMvc.perform(get("/api/product-option-values/{id}", productOptionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionValue.getId().intValue()))
            .andExpect(jsonPath("$.productOptionValueImage").value(DEFAULT_PRODUCT_OPTION_VALUE_IMAGE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.productOptionValueSortOrder").value(DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER))
            .andExpect(jsonPath("$.productOptionDisplayOnly").value(DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductOptionValue() throws Exception {
        // Get the productOptionValue
        restProductOptionValueMockMvc.perform(get("/api/product-option-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOptionValue() throws Exception {
        // Initialize the database
        productOptionValueService.save(productOptionValue);

        int databaseSizeBeforeUpdate = productOptionValueRepository.findAll().size();

        // Update the productOptionValue
        ProductOptionValue updatedProductOptionValue = productOptionValueRepository.findOne(productOptionValue.getId());
        updatedProductOptionValue
            .productOptionValueImage(UPDATED_PRODUCT_OPTION_VALUE_IMAGE)
            .code(UPDATED_CODE)
            .productOptionValueSortOrder(UPDATED_PRODUCT_OPTION_VALUE_SORT_ORDER)
            .productOptionDisplayOnly(UPDATED_PRODUCT_OPTION_DISPLAY_ONLY);

        restProductOptionValueMockMvc.perform(put("/api/product-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOptionValue)))
            .andExpect(status().isOk());

        // Validate the ProductOptionValue in the database
        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionValue testProductOptionValue = productOptionValueList.get(productOptionValueList.size() - 1);
        assertThat(testProductOptionValue.getProductOptionValueImage()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE_IMAGE);
        assertThat(testProductOptionValue.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOptionValue.getProductOptionValueSortOrder()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE_SORT_ORDER);
        assertThat(testProductOptionValue.isProductOptionDisplayOnly()).isEqualTo(UPDATED_PRODUCT_OPTION_DISPLAY_ONLY);

        // Validate the ProductOptionValue in Elasticsearch
        ProductOptionValue productOptionValueEs = productOptionValueSearchRepository.findOne(testProductOptionValue.getId());
        assertThat(productOptionValueEs).isEqualToComparingFieldByField(testProductOptionValue);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOptionValue() throws Exception {
        int databaseSizeBeforeUpdate = productOptionValueRepository.findAll().size();

        // Create the ProductOptionValue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductOptionValueMockMvc.perform(put("/api/product-option-values")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValue)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionValue in the database
        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductOptionValue() throws Exception {
        // Initialize the database
        productOptionValueService.save(productOptionValue);

        int databaseSizeBeforeDelete = productOptionValueRepository.findAll().size();

        // Get the productOptionValue
        restProductOptionValueMockMvc.perform(delete("/api/product-option-values/{id}", productOptionValue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productOptionValueExistsInEs = productOptionValueSearchRepository.exists(productOptionValue.getId());
        assertThat(productOptionValueExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductOptionValue> productOptionValueList = productOptionValueRepository.findAll();
        assertThat(productOptionValueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductOptionValue() throws Exception {
        // Initialize the database
        productOptionValueService.save(productOptionValue);

        // Search the productOptionValue
        restProductOptionValueMockMvc.perform(get("/api/_search/product-option-values?query=id:" + productOptionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionValueImage").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productOptionValueSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productOptionDisplayOnly").value(hasItem(DEFAULT_PRODUCT_OPTION_DISPLAY_ONLY.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionValue.class);
    }
}
