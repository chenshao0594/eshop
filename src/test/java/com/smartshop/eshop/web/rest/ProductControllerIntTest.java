package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.repository.ProductRepository;
import com.smartshop.eshop.service.ProductService;
import com.smartshop.eshop.repository.search.ProductSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductControllerIntTest {

    private static final BigDecimal DEFAULT_PRODUCT_HEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_HEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRODUCT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_WEIGHT = new BigDecimal(2);

    private static final Boolean DEFAULT_PRODUCT_SHIPEABLE = false;
    private static final Boolean UPDATED_PRODUCT_SHIPEABLE = true;

    private static final Integer DEFAULT_PRODUCT_ORDERED = 1;
    private static final Integer UPDATED_PRODUCT_ORDERED = 2;

    private static final BigDecimal DEFAULT_PRODUCT_REVIEW_AVG = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_REVIEW_AVG = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE_AVAILABLE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AVAILABLE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_PRODUCT_IS_FREE = false;
    private static final Boolean UPDATED_PRODUCT_IS_FREE = true;

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    private static final Integer DEFAULT_PRODUCT_REVIEW_COUNT = 1;
    private static final Integer UPDATED_PRODUCT_REVIEW_COUNT = 2;

    private static final String DEFAULT_REF_SKU = "AAAAAAAAAA";
    private static final String UPDATED_REF_SKU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRODUCT_VIRTUAL = false;
    private static final Boolean UPDATED_PRODUCT_VIRTUAL = true;

    private static final BigDecimal DEFAULT_PRODUCT_WIDTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_WIDTH = new BigDecimal(2);

    private static final Boolean DEFAULT_PRE_ORDER = false;
    private static final Boolean UPDATED_PRE_ORDER = true;

    private static final BigDecimal DEFAULT_PRODUCT_LENGTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_LENGTH = new BigDecimal(2);

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductController productController = new ProductController(productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productController)
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
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productHeight(DEFAULT_PRODUCT_HEIGHT)
            .productWeight(DEFAULT_PRODUCT_WEIGHT)
            .productShipeable(DEFAULT_PRODUCT_SHIPEABLE)
            .productOrdered(DEFAULT_PRODUCT_ORDERED)
            .productReviewAvg(DEFAULT_PRODUCT_REVIEW_AVG)
            .dateAvailable(DEFAULT_DATE_AVAILABLE)
            .sortOrder(DEFAULT_SORT_ORDER)
            .productIsFree(DEFAULT_PRODUCT_IS_FREE)
            .available(DEFAULT_AVAILABLE)
            .productReviewCount(DEFAULT_PRODUCT_REVIEW_COUNT)
            .refSku(DEFAULT_REF_SKU)
            .productVirtual(DEFAULT_PRODUCT_VIRTUAL)
            .productWidth(DEFAULT_PRODUCT_WIDTH)
            .preOrder(DEFAULT_PRE_ORDER)
            .productLength(DEFAULT_PRODUCT_LENGTH)
            .sku(DEFAULT_SKU);
        return product;
    }

    @Before
    public void initTest() {
        productSearchRepository.deleteAll();
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductHeight()).isEqualTo(DEFAULT_PRODUCT_HEIGHT);
        assertThat(testProduct.getProductWeight()).isEqualTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testProduct.isProductShipeable()).isEqualTo(DEFAULT_PRODUCT_SHIPEABLE);
        assertThat(testProduct.getProductOrdered()).isEqualTo(DEFAULT_PRODUCT_ORDERED);
        assertThat(testProduct.getProductReviewAvg()).isEqualTo(DEFAULT_PRODUCT_REVIEW_AVG);
        assertThat(testProduct.getDateAvailable()).isEqualTo(DEFAULT_DATE_AVAILABLE);
        assertThat(testProduct.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProduct.isProductIsFree()).isEqualTo(DEFAULT_PRODUCT_IS_FREE);
        assertThat(testProduct.isAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testProduct.getProductReviewCount()).isEqualTo(DEFAULT_PRODUCT_REVIEW_COUNT);
        assertThat(testProduct.getRefSku()).isEqualTo(DEFAULT_REF_SKU);
        assertThat(testProduct.isProductVirtual()).isEqualTo(DEFAULT_PRODUCT_VIRTUAL);
        assertThat(testProduct.getProductWidth()).isEqualTo(DEFAULT_PRODUCT_WIDTH);
        assertThat(testProduct.isPreOrder()).isEqualTo(DEFAULT_PRE_ORDER);
        assertThat(testProduct.getProductLength()).isEqualTo(DEFAULT_PRODUCT_LENGTH);
        assertThat(testProduct.getSku()).isEqualTo(DEFAULT_SKU);

        // Validate the Product in Elasticsearch
        Product productEs = productSearchRepository.findOne(testProduct.getId());
        assertThat(productEs).isEqualToComparingFieldByField(testProduct);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkuIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSku(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productHeight").value(hasItem(DEFAULT_PRODUCT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(DEFAULT_PRODUCT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productShipeable").value(hasItem(DEFAULT_PRODUCT_SHIPEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productOrdered").value(hasItem(DEFAULT_PRODUCT_ORDERED)))
            .andExpect(jsonPath("$.[*].productReviewAvg").value(hasItem(DEFAULT_PRODUCT_REVIEW_AVG.intValue())))
            .andExpect(jsonPath("$.[*].dateAvailable").value(hasItem(DEFAULT_DATE_AVAILABLE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productIsFree").value(hasItem(DEFAULT_PRODUCT_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productReviewCount").value(hasItem(DEFAULT_PRODUCT_REVIEW_COUNT)))
            .andExpect(jsonPath("$.[*].refSku").value(hasItem(DEFAULT_REF_SKU.toString())))
            .andExpect(jsonPath("$.[*].productVirtual").value(hasItem(DEFAULT_PRODUCT_VIRTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].productWidth").value(hasItem(DEFAULT_PRODUCT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].preOrder").value(hasItem(DEFAULT_PRE_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].productLength").value(hasItem(DEFAULT_PRODUCT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productHeight").value(DEFAULT_PRODUCT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.productWeight").value(DEFAULT_PRODUCT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.productShipeable").value(DEFAULT_PRODUCT_SHIPEABLE.booleanValue()))
            .andExpect(jsonPath("$.productOrdered").value(DEFAULT_PRODUCT_ORDERED))
            .andExpect(jsonPath("$.productReviewAvg").value(DEFAULT_PRODUCT_REVIEW_AVG.intValue()))
            .andExpect(jsonPath("$.dateAvailable").value(DEFAULT_DATE_AVAILABLE.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.productIsFree").value(DEFAULT_PRODUCT_IS_FREE.booleanValue()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()))
            .andExpect(jsonPath("$.productReviewCount").value(DEFAULT_PRODUCT_REVIEW_COUNT))
            .andExpect(jsonPath("$.refSku").value(DEFAULT_REF_SKU.toString()))
            .andExpect(jsonPath("$.productVirtual").value(DEFAULT_PRODUCT_VIRTUAL.booleanValue()))
            .andExpect(jsonPath("$.productWidth").value(DEFAULT_PRODUCT_WIDTH.intValue()))
            .andExpect(jsonPath("$.preOrder").value(DEFAULT_PRE_ORDER.booleanValue()))
            .andExpect(jsonPath("$.productLength").value(DEFAULT_PRODUCT_LENGTH.intValue()))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct
            .productHeight(UPDATED_PRODUCT_HEIGHT)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .productShipeable(UPDATED_PRODUCT_SHIPEABLE)
            .productOrdered(UPDATED_PRODUCT_ORDERED)
            .productReviewAvg(UPDATED_PRODUCT_REVIEW_AVG)
            .dateAvailable(UPDATED_DATE_AVAILABLE)
            .sortOrder(UPDATED_SORT_ORDER)
            .productIsFree(UPDATED_PRODUCT_IS_FREE)
            .available(UPDATED_AVAILABLE)
            .productReviewCount(UPDATED_PRODUCT_REVIEW_COUNT)
            .refSku(UPDATED_REF_SKU)
            .productVirtual(UPDATED_PRODUCT_VIRTUAL)
            .productWidth(UPDATED_PRODUCT_WIDTH)
            .preOrder(UPDATED_PRE_ORDER)
            .productLength(UPDATED_PRODUCT_LENGTH)
            .sku(UPDATED_SKU);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductHeight()).isEqualTo(UPDATED_PRODUCT_HEIGHT);
        assertThat(testProduct.getProductWeight()).isEqualTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testProduct.isProductShipeable()).isEqualTo(UPDATED_PRODUCT_SHIPEABLE);
        assertThat(testProduct.getProductOrdered()).isEqualTo(UPDATED_PRODUCT_ORDERED);
        assertThat(testProduct.getProductReviewAvg()).isEqualTo(UPDATED_PRODUCT_REVIEW_AVG);
        assertThat(testProduct.getDateAvailable()).isEqualTo(UPDATED_DATE_AVAILABLE);
        assertThat(testProduct.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProduct.isProductIsFree()).isEqualTo(UPDATED_PRODUCT_IS_FREE);
        assertThat(testProduct.isAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testProduct.getProductReviewCount()).isEqualTo(UPDATED_PRODUCT_REVIEW_COUNT);
        assertThat(testProduct.getRefSku()).isEqualTo(UPDATED_REF_SKU);
        assertThat(testProduct.isProductVirtual()).isEqualTo(UPDATED_PRODUCT_VIRTUAL);
        assertThat(testProduct.getProductWidth()).isEqualTo(UPDATED_PRODUCT_WIDTH);
        assertThat(testProduct.isPreOrder()).isEqualTo(UPDATED_PRE_ORDER);
        assertThat(testProduct.getProductLength()).isEqualTo(UPDATED_PRODUCT_LENGTH);
        assertThat(testProduct.getSku()).isEqualTo(UPDATED_SKU);

        // Validate the Product in Elasticsearch
        Product productEs = productSearchRepository.findOne(testProduct.getId());
        assertThat(productEs).isEqualToComparingFieldByField(testProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productExistsInEs = productSearchRepository.exists(product.getId());
        assertThat(productExistsInEs).isFalse();

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        // Search the product
        restProductMockMvc.perform(get("/api/_search/products?query=id:" + product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productHeight").value(hasItem(DEFAULT_PRODUCT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(DEFAULT_PRODUCT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productShipeable").value(hasItem(DEFAULT_PRODUCT_SHIPEABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productOrdered").value(hasItem(DEFAULT_PRODUCT_ORDERED)))
            .andExpect(jsonPath("$.[*].productReviewAvg").value(hasItem(DEFAULT_PRODUCT_REVIEW_AVG.intValue())))
            .andExpect(jsonPath("$.[*].dateAvailable").value(hasItem(DEFAULT_DATE_AVAILABLE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productIsFree").value(hasItem(DEFAULT_PRODUCT_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].productReviewCount").value(hasItem(DEFAULT_PRODUCT_REVIEW_COUNT)))
            .andExpect(jsonPath("$.[*].refSku").value(hasItem(DEFAULT_REF_SKU.toString())))
            .andExpect(jsonPath("$.[*].productVirtual").value(hasItem(DEFAULT_PRODUCT_VIRTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].productWidth").value(hasItem(DEFAULT_PRODUCT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].preOrder").value(hasItem(DEFAULT_PRE_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].productLength").value(hasItem(DEFAULT_PRODUCT_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
    }
}
