package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductType;
import com.smartshop.eshop.repository.ProductTypeRepository;
import com.smartshop.eshop.service.ProductTypeService;
import com.smartshop.eshop.repository.search.ProductTypeSearchRepository;
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
 * Test class for the ProductTypeResource REST controller.
 *
 * @see ProductTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductTypeControllerIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_G_ENERALTYPE = "AAAAAAAAAA";
    private static final String UPDATED_G_ENERALTYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ALLOW_ADD_TO_CART = false;
    private static final Boolean UPDATED_ALLOW_ADD_TO_CART = true;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductTypeSearchRepository productTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductTypeMockMvc;

    private ProductType productType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductTypeController productTypeController = new ProductTypeController(productTypeService);
        this.restProductTypeMockMvc = MockMvcBuilders.standaloneSetup(productTypeController)
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
    public static ProductType createEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .code(DEFAULT_CODE)
            .allowAddToCart(DEFAULT_ALLOW_ADD_TO_CART);
        return productType;
    }

    @Before
    public void initTest() {
        productTypeSearchRepository.deleteAll();
        productType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductType() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isCreated());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductType.isAllowAddToCart()).isEqualTo(DEFAULT_ALLOW_ADD_TO_CART);

        // Validate the ProductType in Elasticsearch
        ProductType productTypeEs = productTypeSearchRepository.findOne(testProductType.getId());
        assertThat(productTypeEs).isEqualToComparingFieldByField(testProductType);
    }

    @Test
    @Transactional
    public void createProductTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // Create the ProductType with an existing ID
        productType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTypeMockMvc.perform(post("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductTypes() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList
        restProductTypeMockMvc.perform(get("/api/product-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].gENERALTYPE").value(hasItem(DEFAULT_G_ENERALTYPE.toString())))
            .andExpect(jsonPath("$.[*].allowAddToCart").value(hasItem(DEFAULT_ALLOW_ADD_TO_CART.booleanValue())));
    }

    @Test
    @Transactional
    public void getProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.gENERALTYPE").value(DEFAULT_G_ENERALTYPE.toString()))
            .andExpect(jsonPath("$.allowAddToCart").value(DEFAULT_ALLOW_ADD_TO_CART.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductType() throws Exception {
        // Get the productType
        restProductTypeMockMvc.perform(get("/api/product-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductType() throws Exception {
        // Initialize the database
        productTypeService.save(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType
        ProductType updatedProductType = productTypeRepository.findOne(productType.getId());
        updatedProductType
            .code(UPDATED_CODE)
            .allowAddToCart(UPDATED_ALLOW_ADD_TO_CART);

        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductType)))
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductType.isAllowAddToCart()).isEqualTo(UPDATED_ALLOW_ADD_TO_CART);

        // Validate the ProductType in Elasticsearch
        ProductType productTypeEs = productTypeSearchRepository.findOne(testProductType.getId());
        assertThat(productTypeEs).isEqualToComparingFieldByField(testProductType);
    }

    @Test
    @Transactional
    public void updateNonExistingProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Create the ProductType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductTypeMockMvc.perform(put("/api/product-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productType)))
            .andExpect(status().isCreated());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductType() throws Exception {
        // Initialize the database
        productTypeService.save(productType);

        int databaseSizeBeforeDelete = productTypeRepository.findAll().size();

        // Get the productType
        restProductTypeMockMvc.perform(delete("/api/product-types/{id}", productType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productTypeExistsInEs = productTypeSearchRepository.exists(productType.getId());
        assertThat(productTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductType() throws Exception {
        // Initialize the database
        productTypeService.save(productType);

        // Search the productType
        restProductTypeMockMvc.perform(get("/api/_search/product-types?query=id:" + productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].gENERALTYPE").value(hasItem(DEFAULT_G_ENERALTYPE.toString())))
            .andExpect(jsonPath("$.[*].allowAddToCart").value(hasItem(DEFAULT_ALLOW_ADD_TO_CART.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductType.class);
    }
}
