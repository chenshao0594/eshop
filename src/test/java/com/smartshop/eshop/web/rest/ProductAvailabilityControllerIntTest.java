package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductAvailability;
import com.smartshop.eshop.repository.ProductAvailabilityRepository;
import com.smartshop.eshop.service.ProductAvailabilityService;
import com.smartshop.eshop.repository.search.ProductAvailabilitySearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductAvailabilityResource REST controller.
 *
 * @see ProductAvailabilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductAvailabilityControllerIntTest {

    private static final Integer DEFAULT_PRODUCT_QUANTITY_ORDER_MAX = 1;
    private static final Integer UPDATED_PRODUCT_QUANTITY_ORDER_MAX = 2;

    private static final Boolean DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING = false;
    private static final Boolean UPDATED_PRODUCT_IS_ALWAYS_FREE_SHIPPING = true;

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_QUANTITY = 1;
    private static final Integer UPDATED_PRODUCT_QUANTITY = 2;

    private static final Integer DEFAULT_PRODUCT_QUANTITY_ORDER_MIN = 1;
    private static final Integer UPDATED_PRODUCT_QUANTITY_ORDER_MIN = 2;

    private static final LocalDate DEFAULT_PRODUCT_DATE_AVAILABLE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_DATE_AVAILABLE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PRODUCT_STATUS = false;
    private static final Boolean UPDATED_PRODUCT_STATUS = true;

    private static final String DEFAULT_REGION_VARIANT = "AAAAAAAAAA";
    private static final String UPDATED_REGION_VARIANT = "BBBBBBBBBB";

    @Autowired
    private ProductAvailabilityRepository productAvailabilityRepository;

    @Autowired
    private ProductAvailabilityService productAvailabilityService;

    @Autowired
    private ProductAvailabilitySearchRepository productAvailabilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductAvailabilityMockMvc;

    private ProductAvailability productAvailability;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductAvailabilityController productAvailabilityController = new ProductAvailabilityController(productAvailabilityService);
        this.restProductAvailabilityMockMvc = MockMvcBuilders.standaloneSetup(productAvailabilityController)
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
    public static ProductAvailability createEntity(EntityManager em) {
        ProductAvailability productAvailability = new ProductAvailability()
            .productQuantityOrderMax(DEFAULT_PRODUCT_QUANTITY_ORDER_MAX)
            .productIsAlwaysFreeShipping(DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING)
            .region(DEFAULT_REGION)
            .productQuantity(DEFAULT_PRODUCT_QUANTITY)
            .productQuantityOrderMin(DEFAULT_PRODUCT_QUANTITY_ORDER_MIN)
            .productDateAvailable(DEFAULT_PRODUCT_DATE_AVAILABLE)
            .productStatus(DEFAULT_PRODUCT_STATUS)
            .regionVariant(DEFAULT_REGION_VARIANT);
        return productAvailability;
    }

    @Before
    public void initTest() {
        productAvailabilitySearchRepository.deleteAll();
        productAvailability = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAvailability() throws Exception {
        int databaseSizeBeforeCreate = productAvailabilityRepository.findAll().size();

        // Create the ProductAvailability
        restProductAvailabilityMockMvc.perform(post("/api/product-availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAvailability)))
            .andExpect(status().isCreated());

        // Validate the ProductAvailability in the database
        List<ProductAvailability> productAvailabilityList = productAvailabilityRepository.findAll();
        assertThat(productAvailabilityList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAvailability testProductAvailability = productAvailabilityList.get(productAvailabilityList.size() - 1);
        assertThat(testProductAvailability.getProductQuantityOrderMax()).isEqualTo(DEFAULT_PRODUCT_QUANTITY_ORDER_MAX);
        assertThat(testProductAvailability.isProductIsAlwaysFreeShipping()).isEqualTo(DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING);
        assertThat(testProductAvailability.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testProductAvailability.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testProductAvailability.getProductQuantityOrderMin()).isEqualTo(DEFAULT_PRODUCT_QUANTITY_ORDER_MIN);
        assertThat(testProductAvailability.getProductDateAvailable()).isEqualTo(DEFAULT_PRODUCT_DATE_AVAILABLE);
        assertThat(testProductAvailability.isProductStatus()).isEqualTo(DEFAULT_PRODUCT_STATUS);
        assertThat(testProductAvailability.getRegionVariant()).isEqualTo(DEFAULT_REGION_VARIANT);

        // Validate the ProductAvailability in Elasticsearch
        ProductAvailability productAvailabilityEs = productAvailabilitySearchRepository.findOne(testProductAvailability.getId());
        assertThat(productAvailabilityEs).isEqualToComparingFieldByField(testProductAvailability);
    }

    @Test
    @Transactional
    public void createProductAvailabilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAvailabilityRepository.findAll().size();

        // Create the ProductAvailability with an existing ID
        productAvailability.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAvailabilityMockMvc.perform(post("/api/product-availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAvailability)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductAvailability> productAvailabilityList = productAvailabilityRepository.findAll();
        assertThat(productAvailabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductAvailabilities() throws Exception {
        // Initialize the database
        productAvailabilityRepository.saveAndFlush(productAvailability);

        // Get all the productAvailabilityList
        restProductAvailabilityMockMvc.perform(get("/api/product-availabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].productQuantityOrderMax").value(hasItem(DEFAULT_PRODUCT_QUANTITY_ORDER_MAX)))
            .andExpect(jsonPath("$.[*].productIsAlwaysFreeShipping").value(hasItem(DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY)))
            .andExpect(jsonPath("$.[*].productQuantityOrderMin").value(hasItem(DEFAULT_PRODUCT_QUANTITY_ORDER_MIN)))
            .andExpect(jsonPath("$.[*].productDateAvailable").value(hasItem(DEFAULT_PRODUCT_DATE_AVAILABLE.toString())))
            .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].regionVariant").value(hasItem(DEFAULT_REGION_VARIANT.toString())));
    }

    @Test
    @Transactional
    public void getProductAvailability() throws Exception {
        // Initialize the database
        productAvailabilityRepository.saveAndFlush(productAvailability);

        // Get the productAvailability
        restProductAvailabilityMockMvc.perform(get("/api/product-availabilities/{id}", productAvailability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAvailability.getId().intValue()))
            .andExpect(jsonPath("$.productQuantityOrderMax").value(DEFAULT_PRODUCT_QUANTITY_ORDER_MAX))
            .andExpect(jsonPath("$.productIsAlwaysFreeShipping").value(DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING.booleanValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.productQuantity").value(DEFAULT_PRODUCT_QUANTITY))
            .andExpect(jsonPath("$.productQuantityOrderMin").value(DEFAULT_PRODUCT_QUANTITY_ORDER_MIN))
            .andExpect(jsonPath("$.productDateAvailable").value(DEFAULT_PRODUCT_DATE_AVAILABLE.toString()))
            .andExpect(jsonPath("$.productStatus").value(DEFAULT_PRODUCT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.regionVariant").value(DEFAULT_REGION_VARIANT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductAvailability() throws Exception {
        // Get the productAvailability
        restProductAvailabilityMockMvc.perform(get("/api/product-availabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAvailability() throws Exception {
        // Initialize the database
        productAvailabilityService.save(productAvailability);

        int databaseSizeBeforeUpdate = productAvailabilityRepository.findAll().size();

        // Update the productAvailability
        ProductAvailability updatedProductAvailability = productAvailabilityRepository.findOne(productAvailability.getId());
        updatedProductAvailability
            .productQuantityOrderMax(UPDATED_PRODUCT_QUANTITY_ORDER_MAX)
            .productIsAlwaysFreeShipping(UPDATED_PRODUCT_IS_ALWAYS_FREE_SHIPPING)
            .region(UPDATED_REGION)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .productQuantityOrderMin(UPDATED_PRODUCT_QUANTITY_ORDER_MIN)
            .productDateAvailable(UPDATED_PRODUCT_DATE_AVAILABLE)
            .productStatus(UPDATED_PRODUCT_STATUS)
            .regionVariant(UPDATED_REGION_VARIANT);

        restProductAvailabilityMockMvc.perform(put("/api/product-availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductAvailability)))
            .andExpect(status().isOk());

        // Validate the ProductAvailability in the database
        List<ProductAvailability> productAvailabilityList = productAvailabilityRepository.findAll();
        assertThat(productAvailabilityList).hasSize(databaseSizeBeforeUpdate);
        ProductAvailability testProductAvailability = productAvailabilityList.get(productAvailabilityList.size() - 1);
        assertThat(testProductAvailability.getProductQuantityOrderMax()).isEqualTo(UPDATED_PRODUCT_QUANTITY_ORDER_MAX);
        assertThat(testProductAvailability.isProductIsAlwaysFreeShipping()).isEqualTo(UPDATED_PRODUCT_IS_ALWAYS_FREE_SHIPPING);
        assertThat(testProductAvailability.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testProductAvailability.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testProductAvailability.getProductQuantityOrderMin()).isEqualTo(UPDATED_PRODUCT_QUANTITY_ORDER_MIN);
        assertThat(testProductAvailability.getProductDateAvailable()).isEqualTo(UPDATED_PRODUCT_DATE_AVAILABLE);
        assertThat(testProductAvailability.isProductStatus()).isEqualTo(UPDATED_PRODUCT_STATUS);
        assertThat(testProductAvailability.getRegionVariant()).isEqualTo(UPDATED_REGION_VARIANT);

        // Validate the ProductAvailability in Elasticsearch
        ProductAvailability productAvailabilityEs = productAvailabilitySearchRepository.findOne(testProductAvailability.getId());
        assertThat(productAvailabilityEs).isEqualToComparingFieldByField(testProductAvailability);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAvailability() throws Exception {
        int databaseSizeBeforeUpdate = productAvailabilityRepository.findAll().size();

        // Create the ProductAvailability

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductAvailabilityMockMvc.perform(put("/api/product-availabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAvailability)))
            .andExpect(status().isCreated());

        // Validate the ProductAvailability in the database
        List<ProductAvailability> productAvailabilityList = productAvailabilityRepository.findAll();
        assertThat(productAvailabilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductAvailability() throws Exception {
        // Initialize the database
        productAvailabilityService.save(productAvailability);

        int databaseSizeBeforeDelete = productAvailabilityRepository.findAll().size();

        // Get the productAvailability
        restProductAvailabilityMockMvc.perform(delete("/api/product-availabilities/{id}", productAvailability.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productAvailabilityExistsInEs = productAvailabilitySearchRepository.exists(productAvailability.getId());
        assertThat(productAvailabilityExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductAvailability> productAvailabilityList = productAvailabilityRepository.findAll();
        assertThat(productAvailabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductAvailability() throws Exception {
        // Initialize the database
        productAvailabilityService.save(productAvailability);

        // Search the productAvailability
        restProductAvailabilityMockMvc.perform(get("/api/_search/product-availabilities?query=id:" + productAvailability.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAvailability.getId().intValue())))
            .andExpect(jsonPath("$.[*].productQuantityOrderMax").value(hasItem(DEFAULT_PRODUCT_QUANTITY_ORDER_MAX)))
            .andExpect(jsonPath("$.[*].productIsAlwaysFreeShipping").value(hasItem(DEFAULT_PRODUCT_IS_ALWAYS_FREE_SHIPPING.booleanValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY)))
            .andExpect(jsonPath("$.[*].productQuantityOrderMin").value(hasItem(DEFAULT_PRODUCT_QUANTITY_ORDER_MIN)))
            .andExpect(jsonPath("$.[*].productDateAvailable").value(hasItem(DEFAULT_PRODUCT_DATE_AVAILABLE.toString())))
            .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].regionVariant").value(hasItem(DEFAULT_REGION_VARIANT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAvailability.class);
    }
}
