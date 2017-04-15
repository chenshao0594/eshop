package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.repository.OrderProductRepository;
import com.smartshop.eshop.service.OrderProductService;
import com.smartshop.eshop.repository.search.OrderProductSearchRepository;
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
 * Test class for the OrderProductResource REST controller.
 *
 * @see OrderProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderProductControllerIntTest {

    private static final Integer DEFAULT_PRODUCT_QUANTITY = 1;
    private static final Integer UPDATED_PRODUCT_QUANTITY = 2;

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ONE_TIME_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ONE_TIME_CHARGE = new BigDecimal(2);

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private OrderProductSearchRepository orderProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderProductMockMvc;

    private OrderProduct orderProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderProductController orderProductController = new OrderProductController(orderProductService);
        this.restOrderProductMockMvc = MockMvcBuilders.standaloneSetup(orderProductController)
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
    public static OrderProduct createEntity(EntityManager em) {
        OrderProduct orderProduct = new OrderProduct()
            .productQuantity(DEFAULT_PRODUCT_QUANTITY)
            .sku(DEFAULT_SKU)
            .oneTimeCharge(DEFAULT_ONE_TIME_CHARGE)
            .productName(DEFAULT_PRODUCT_NAME);
        return orderProduct;
    }

    @Before
    public void initTest() {
        orderProductSearchRepository.deleteAll();
        orderProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderProduct() throws Exception {
        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();

        // Create the OrderProduct
        restOrderProductMockMvc.perform(post("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isCreated());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProduct testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testOrderProduct.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testOrderProduct.getOneTimeCharge()).isEqualTo(DEFAULT_ONE_TIME_CHARGE);
        assertThat(testOrderProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);

        // Validate the OrderProduct in Elasticsearch
        OrderProduct orderProductEs = orderProductSearchRepository.findOne(testOrderProduct.getId());
        assertThat(orderProductEs).isEqualToComparingFieldByField(testOrderProduct);
    }

    @Test
    @Transactional
    public void createOrderProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();

        // Create the OrderProduct with an existing ID
        orderProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductMockMvc.perform(post("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderProducts() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get all the orderProductList
        restOrderProductMockMvc.perform(get("/api/order-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())))
            .andExpect(jsonPath("$.[*].oneTimeCharge").value(hasItem(DEFAULT_ONE_TIME_CHARGE.intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get the orderProduct
        restOrderProductMockMvc.perform(get("/api/order-products/{id}", orderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderProduct.getId().intValue()))
            .andExpect(jsonPath("$.productQuantity").value(DEFAULT_PRODUCT_QUANTITY))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU.toString()))
            .andExpect(jsonPath("$.oneTimeCharge").value(DEFAULT_ONE_TIME_CHARGE.intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderProduct() throws Exception {
        // Get the orderProduct
        restOrderProductMockMvc.perform(get("/api/order-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderProduct() throws Exception {
        // Initialize the database
        orderProductService.save(orderProduct);

        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Update the orderProduct
        OrderProduct updatedOrderProduct = orderProductRepository.findOne(orderProduct.getId());
        updatedOrderProduct
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .sku(UPDATED_SKU)
            .oneTimeCharge(UPDATED_ONE_TIME_CHARGE)
            .productName(UPDATED_PRODUCT_NAME);

        restOrderProductMockMvc.perform(put("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderProduct)))
            .andExpect(status().isOk());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
        OrderProduct testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testOrderProduct.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testOrderProduct.getOneTimeCharge()).isEqualTo(UPDATED_ONE_TIME_CHARGE);
        assertThat(testOrderProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);

        // Validate the OrderProduct in Elasticsearch
        OrderProduct orderProductEs = orderProductSearchRepository.findOne(testOrderProduct.getId());
        assertThat(orderProductEs).isEqualToComparingFieldByField(testOrderProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Create the OrderProduct

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderProductMockMvc.perform(put("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProduct)))
            .andExpect(status().isCreated());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderProduct() throws Exception {
        // Initialize the database
        orderProductService.save(orderProduct);

        int databaseSizeBeforeDelete = orderProductRepository.findAll().size();

        // Get the orderProduct
        restOrderProductMockMvc.perform(delete("/api/order-products/{id}", orderProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderProductExistsInEs = orderProductSearchRepository.exists(orderProduct.getId());
        assertThat(orderProductExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderProduct() throws Exception {
        // Initialize the database
        orderProductService.save(orderProduct);

        // Search the orderProduct
        restOrderProductMockMvc.perform(get("/api/_search/order-products?query=id:" + orderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU.toString())))
            .andExpect(jsonPath("$.[*].oneTimeCharge").value(hasItem(DEFAULT_ONE_TIME_CHARGE.intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProduct.class);
    }
}
