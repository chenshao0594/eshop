package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderAccountProduct;
import com.smartshop.eshop.repository.OrderAccountProductRepository;
import com.smartshop.eshop.service.OrderAccountProductService;
import com.smartshop.eshop.repository.search.OrderAccountProductSearchRepository;
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
 * Test class for the OrderAccountProductResource REST controller.
 *
 * @see OrderAccountProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderAccountProductControllerIntTest {

    private static final Long DEFAULT_ORDER_ACCOUNT_PRODUCT_ID = 1L;
    private static final Long UPDATED_ORDER_ACCOUNT_PRODUCT_ID = 2L;

    private static final Integer DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS = 1;
    private static final Integer UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS = 2;

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_PRODUCT_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_PRODUCT_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS = 1;
    private static final Integer UPDATED_ORDER_ACCOUNT_PRODUCT_STATUS = 2;

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE = 1;
    private static final Integer UPDATED_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE = 2;

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_PRODUCT_EOT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OrderAccountProductRepository orderAccountProductRepository;

    @Autowired
    private OrderAccountProductService orderAccountProductService;

    @Autowired
    private OrderAccountProductSearchRepository orderAccountProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderAccountProductMockMvc;

    private OrderAccountProduct orderAccountProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderAccountProductController orderAccountProductController = new OrderAccountProductController(orderAccountProductService);
        this.restOrderAccountProductMockMvc = MockMvcBuilders.standaloneSetup(orderAccountProductController)
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
    public static OrderAccountProduct createEntity(EntityManager em) {
        OrderAccountProduct orderAccountProduct = new OrderAccountProduct()
            .orderAccountProductId(DEFAULT_ORDER_ACCOUNT_PRODUCT_ID)
            .orderAccountProductLastTransactionStatus(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS)
            .orderAccountProductEndDate(DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE)
            .orderAccountProductStartDate(DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE)
            .orderAccountProductLastStatusDate(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE)
            .orderAccountProductStatus(DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS)
            .orderAccountProductAccountedDate(DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE)
            .orderAccountProductPaymentFrequencyType(DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE)
            .orderAccountProductEot(DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT);
        return orderAccountProduct;
    }

    @Before
    public void initTest() {
        orderAccountProductSearchRepository.deleteAll();
        orderAccountProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderAccountProduct() throws Exception {
        int databaseSizeBeforeCreate = orderAccountProductRepository.findAll().size();

        // Create the OrderAccountProduct
        restOrderAccountProductMockMvc.perform(post("/api/order-account-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccountProduct)))
            .andExpect(status().isCreated());

        // Validate the OrderAccountProduct in the database
        List<OrderAccountProduct> orderAccountProductList = orderAccountProductRepository.findAll();
        assertThat(orderAccountProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrderAccountProduct testOrderAccountProduct = orderAccountProductList.get(orderAccountProductList.size() - 1);
        assertThat(testOrderAccountProduct.getOrderAccountProductId()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_ID);
        assertThat(testOrderAccountProduct.getOrderAccountProductLastTransactionStatus()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS);
        assertThat(testOrderAccountProduct.getOrderAccountProductEndDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductStartDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductLastStatusDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductStatus()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS);
        assertThat(testOrderAccountProduct.getOrderAccountProductAccountedDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductPaymentFrequencyType()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE);
        assertThat(testOrderAccountProduct.getOrderAccountProductEot()).isEqualTo(DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT);

        // Validate the OrderAccountProduct in Elasticsearch
        OrderAccountProduct orderAccountProductEs = orderAccountProductSearchRepository.findOne(testOrderAccountProduct.getId());
        assertThat(orderAccountProductEs).isEqualToComparingFieldByField(testOrderAccountProduct);
    }

    @Test
    @Transactional
    public void createOrderAccountProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderAccountProductRepository.findAll().size();

        // Create the OrderAccountProduct with an existing ID
        orderAccountProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderAccountProductMockMvc.perform(post("/api/order-account-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccountProduct)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderAccountProduct> orderAccountProductList = orderAccountProductRepository.findAll();
        assertThat(orderAccountProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderAccountProducts() throws Exception {
        // Initialize the database
        orderAccountProductRepository.saveAndFlush(orderAccountProduct);

        // Get all the orderAccountProductList
        restOrderAccountProductMockMvc.perform(get("/api/order-account-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderAccountProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderAccountProductId").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderAccountProductLastTransactionStatus").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS)))
            .andExpect(jsonPath("$.[*].orderAccountProductEndDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductStartDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductLastStatusDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductStatus").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS)))
            .andExpect(jsonPath("$.[*].orderAccountProductAccountedDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductPaymentFrequencyType").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE)))
            .andExpect(jsonPath("$.[*].orderAccountProductEot").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT.toString())));
    }

    @Test
    @Transactional
    public void getOrderAccountProduct() throws Exception {
        // Initialize the database
        orderAccountProductRepository.saveAndFlush(orderAccountProduct);

        // Get the orderAccountProduct
        restOrderAccountProductMockMvc.perform(get("/api/order-account-products/{id}", orderAccountProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderAccountProduct.getId().intValue()))
            .andExpect(jsonPath("$.orderAccountProductId").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.orderAccountProductLastTransactionStatus").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS))
            .andExpect(jsonPath("$.orderAccountProductEndDate").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountProductStartDate").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountProductLastStatusDate").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountProductStatus").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS))
            .andExpect(jsonPath("$.orderAccountProductAccountedDate").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountProductPaymentFrequencyType").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE))
            .andExpect(jsonPath("$.orderAccountProductEot").value(DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderAccountProduct() throws Exception {
        // Get the orderAccountProduct
        restOrderAccountProductMockMvc.perform(get("/api/order-account-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderAccountProduct() throws Exception {
        // Initialize the database
        orderAccountProductService.save(orderAccountProduct);

        int databaseSizeBeforeUpdate = orderAccountProductRepository.findAll().size();

        // Update the orderAccountProduct
        OrderAccountProduct updatedOrderAccountProduct = orderAccountProductRepository.findOne(orderAccountProduct.getId());
        updatedOrderAccountProduct
            .orderAccountProductId(UPDATED_ORDER_ACCOUNT_PRODUCT_ID)
            .orderAccountProductLastTransactionStatus(UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS)
            .orderAccountProductEndDate(UPDATED_ORDER_ACCOUNT_PRODUCT_END_DATE)
            .orderAccountProductStartDate(UPDATED_ORDER_ACCOUNT_PRODUCT_START_DATE)
            .orderAccountProductLastStatusDate(UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE)
            .orderAccountProductStatus(UPDATED_ORDER_ACCOUNT_PRODUCT_STATUS)
            .orderAccountProductAccountedDate(UPDATED_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE)
            .orderAccountProductPaymentFrequencyType(UPDATED_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE)
            .orderAccountProductEot(UPDATED_ORDER_ACCOUNT_PRODUCT_EOT);

        restOrderAccountProductMockMvc.perform(put("/api/order-account-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderAccountProduct)))
            .andExpect(status().isOk());

        // Validate the OrderAccountProduct in the database
        List<OrderAccountProduct> orderAccountProductList = orderAccountProductRepository.findAll();
        assertThat(orderAccountProductList).hasSize(databaseSizeBeforeUpdate);
        OrderAccountProduct testOrderAccountProduct = orderAccountProductList.get(orderAccountProductList.size() - 1);
        assertThat(testOrderAccountProduct.getOrderAccountProductId()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_ID);
        assertThat(testOrderAccountProduct.getOrderAccountProductLastTransactionStatus()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS);
        assertThat(testOrderAccountProduct.getOrderAccountProductEndDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_END_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductStartDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_START_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductLastStatusDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductStatus()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_STATUS);
        assertThat(testOrderAccountProduct.getOrderAccountProductAccountedDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE);
        assertThat(testOrderAccountProduct.getOrderAccountProductPaymentFrequencyType()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE);
        assertThat(testOrderAccountProduct.getOrderAccountProductEot()).isEqualTo(UPDATED_ORDER_ACCOUNT_PRODUCT_EOT);

        // Validate the OrderAccountProduct in Elasticsearch
        OrderAccountProduct orderAccountProductEs = orderAccountProductSearchRepository.findOne(testOrderAccountProduct.getId());
        assertThat(orderAccountProductEs).isEqualToComparingFieldByField(testOrderAccountProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderAccountProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderAccountProductRepository.findAll().size();

        // Create the OrderAccountProduct

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderAccountProductMockMvc.perform(put("/api/order-account-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccountProduct)))
            .andExpect(status().isCreated());

        // Validate the OrderAccountProduct in the database
        List<OrderAccountProduct> orderAccountProductList = orderAccountProductRepository.findAll();
        assertThat(orderAccountProductList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderAccountProduct() throws Exception {
        // Initialize the database
        orderAccountProductService.save(orderAccountProduct);

        int databaseSizeBeforeDelete = orderAccountProductRepository.findAll().size();

        // Get the orderAccountProduct
        restOrderAccountProductMockMvc.perform(delete("/api/order-account-products/{id}", orderAccountProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderAccountProductExistsInEs = orderAccountProductSearchRepository.exists(orderAccountProduct.getId());
        assertThat(orderAccountProductExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderAccountProduct> orderAccountProductList = orderAccountProductRepository.findAll();
        assertThat(orderAccountProductList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderAccountProduct() throws Exception {
        // Initialize the database
        orderAccountProductService.save(orderAccountProduct);

        // Search the orderAccountProduct
        restOrderAccountProductMockMvc.perform(get("/api/_search/order-account-products?query=id:" + orderAccountProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderAccountProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderAccountProductId").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderAccountProductLastTransactionStatus").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_TRANSACTION_STATUS)))
            .andExpect(jsonPath("$.[*].orderAccountProductEndDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductStartDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductLastStatusDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_LAST_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductStatus").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_STATUS)))
            .andExpect(jsonPath("$.[*].orderAccountProductAccountedDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_ACCOUNTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountProductPaymentFrequencyType").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_PAYMENT_FREQUENCY_TYPE)))
            .andExpect(jsonPath("$.[*].orderAccountProductEot").value(hasItem(DEFAULT_ORDER_ACCOUNT_PRODUCT_EOT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderAccountProduct.class);
    }
}
