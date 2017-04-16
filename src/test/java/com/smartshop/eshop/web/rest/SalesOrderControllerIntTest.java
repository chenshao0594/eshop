package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.repository.SalesOrderRepository;
import com.smartshop.eshop.service.SalesOrderService;
import com.smartshop.eshop.repository.search.SalesOrderSearchRepository;
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

import com.smartshop.eshop.domain.enumeration.PaymentEnum;
import com.smartshop.eshop.domain.enumeration.OrderChannelEnum;
import com.smartshop.eshop.domain.enumeration.OrderEnum;
import com.smartshop.eshop.domain.enumeration.OrderStatusEnum;
/**
 * Test class for the SalesOrderResource REST controller.
 *
 * @see SalesOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class SalesOrderControllerIntTest {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final Boolean DEFAULT_CONFIRMED_ADDRESS = false;
    private static final Boolean UPDATED_CONFIRMED_ADDRESS = true;

    private static final LocalDate DEFAULT_ORDER_DATE_FINISHED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE_FINISHED = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    private static final String DEFAULT_PAYMENT_MODULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_MODULE_CODE = "BBBBBBBBBB";

    private static final PaymentEnum DEFAULT_PAYMENT_TYPE = PaymentEnum.CREDITCARD;
    private static final PaymentEnum UPDATED_PAYMENT_TYPE = PaymentEnum.FREE;

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final OrderChannelEnum DEFAULT_CHANNEL = OrderChannelEnum.ONLINE;
    private static final OrderChannelEnum UPDATED_CHANNEL = OrderChannelEnum.ONLINE;

    private static final String DEFAULT_CUSTOMER_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final OrderEnum DEFAULT_ORDER_TYPE = OrderEnum.ORDER;
    private static final OrderEnum UPDATED_ORDER_TYPE = OrderEnum.BOOKING;

    private static final OrderStatusEnum DEFAULT_STATUS = OrderStatusEnum.ORDERED;
    private static final OrderStatusEnum UPDATED_STATUS = OrderStatusEnum.PROCESSED;

    private static final LocalDate DEFAULT_LAST_MODIFIED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_MODIFIED = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_CURRENCY_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CURRENCY_VALUE = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATE_PURCHASED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PURCHASED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SHIPPING_MODULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_MODULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CUSTOMER_AGREEMENT = false;
    private static final Boolean UPDATED_CUSTOMER_AGREEMENT = true;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SalesOrderSearchRepository salesOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSalesOrderMockMvc;

    private SalesOrder salesOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalesOrderController salesOrderController = new SalesOrderController(salesOrderService);
        this.restSalesOrderMockMvc = MockMvcBuilders.standaloneSetup(salesOrderController)
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
    public static SalesOrder createEntity(EntityManager em) {
        SalesOrder salesOrder = new SalesOrder()
            .customerId(DEFAULT_CUSTOMER_ID)
            .confirmedAddress(DEFAULT_CONFIRMED_ADDRESS)
            .orderDateFinished(DEFAULT_ORDER_DATE_FINISHED)
            .total(DEFAULT_TOTAL)
            .paymentModuleCode(DEFAULT_PAYMENT_MODULE_CODE)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .locale(DEFAULT_LOCALE)
            .channel(DEFAULT_CHANNEL)
            .customerEmailAddress(DEFAULT_CUSTOMER_EMAIL_ADDRESS)
            .orderType(DEFAULT_ORDER_TYPE)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .currencyValue(DEFAULT_CURRENCY_VALUE)
            .datePurchased(DEFAULT_DATE_PURCHASED)
            .shippingModuleCode(DEFAULT_SHIPPING_MODULE_CODE)
            .ipAddress(DEFAULT_IP_ADDRESS)
            .customerAgreement(DEFAULT_CUSTOMER_AGREEMENT);
        return salesOrder;
    }

    @Before
    public void initTest() {
        salesOrderSearchRepository.deleteAll();
        salesOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesOrder() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder
        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate + 1);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testSalesOrder.isConfirmedAddress()).isEqualTo(DEFAULT_CONFIRMED_ADDRESS);
        assertThat(testSalesOrder.getOrderDateFinished()).isEqualTo(DEFAULT_ORDER_DATE_FINISHED);
        assertThat(testSalesOrder.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testSalesOrder.getPaymentModuleCode()).isEqualTo(DEFAULT_PAYMENT_MODULE_CODE);
        assertThat(testSalesOrder.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testSalesOrder.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testSalesOrder.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testSalesOrder.getCustomerEmailAddress()).isEqualTo(DEFAULT_CUSTOMER_EMAIL_ADDRESS);
        assertThat(testSalesOrder.getOrderType()).isEqualTo(DEFAULT_ORDER_TYPE);
        assertThat(testSalesOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSalesOrder.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSalesOrder.getCurrencyValue()).isEqualTo(DEFAULT_CURRENCY_VALUE);
        assertThat(testSalesOrder.getDatePurchased()).isEqualTo(DEFAULT_DATE_PURCHASED);
        assertThat(testSalesOrder.getShippingModuleCode()).isEqualTo(DEFAULT_SHIPPING_MODULE_CODE);
        assertThat(testSalesOrder.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
        assertThat(testSalesOrder.isCustomerAgreement()).isEqualTo(DEFAULT_CUSTOMER_AGREEMENT);

        // Validate the SalesOrder in Elasticsearch
        SalesOrder salesOrderEs = salesOrderSearchRepository.findOne(testSalesOrder.getId());
        assertThat(salesOrderEs).isEqualToComparingFieldByField(testSalesOrder);
    }

    @Test
    @Transactional
    public void createSalesOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesOrderRepository.findAll().size();

        // Create the SalesOrder with an existing ID
        salesOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesOrderMockMvc.perform(post("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalesOrders() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get all the salesOrderList
        restSalesOrderMockMvc.perform(get("/api/sales-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].confirmedAddress").value(hasItem(DEFAULT_CONFIRMED_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].orderDateFinished").value(hasItem(DEFAULT_ORDER_DATE_FINISHED.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].paymentModuleCode").value(hasItem(DEFAULT_PAYMENT_MODULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].customerEmailAddress").value(hasItem(DEFAULT_CUSTOMER_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].currencyValue").value(hasItem(DEFAULT_CURRENCY_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].datePurchased").value(hasItem(DEFAULT_DATE_PURCHASED.toString())))
            .andExpect(jsonPath("$.[*].shippingModuleCode").value(hasItem(DEFAULT_SHIPPING_MODULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].customerAgreement").value(hasItem(DEFAULT_CUSTOMER_AGREEMENT.booleanValue())));
    }

    @Test
    @Transactional
    public void getSalesOrder() throws Exception {
        // Initialize the database
        salesOrderRepository.saveAndFlush(salesOrder);

        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salesOrder.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.confirmedAddress").value(DEFAULT_CONFIRMED_ADDRESS.booleanValue()))
            .andExpect(jsonPath("$.orderDateFinished").value(DEFAULT_ORDER_DATE_FINISHED.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()))
            .andExpect(jsonPath("$.paymentModuleCode").value(DEFAULT_PAYMENT_MODULE_CODE.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.customerEmailAddress").value(DEFAULT_CUSTOMER_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.orderType").value(DEFAULT_ORDER_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.currencyValue").value(DEFAULT_CURRENCY_VALUE.intValue()))
            .andExpect(jsonPath("$.datePurchased").value(DEFAULT_DATE_PURCHASED.toString()))
            .andExpect(jsonPath("$.shippingModuleCode").value(DEFAULT_SHIPPING_MODULE_CODE.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()))
            .andExpect(jsonPath("$.customerAgreement").value(DEFAULT_CUSTOMER_AGREEMENT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalesOrder() throws Exception {
        // Get the salesOrder
        restSalesOrderMockMvc.perform(get("/api/sales-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Update the salesOrder
        SalesOrder updatedSalesOrder = salesOrderRepository.findOne(salesOrder.getId());
        updatedSalesOrder
            .customerId(UPDATED_CUSTOMER_ID)
            .confirmedAddress(UPDATED_CONFIRMED_ADDRESS)
            .orderDateFinished(UPDATED_ORDER_DATE_FINISHED)
            .total(UPDATED_TOTAL)
            .paymentModuleCode(UPDATED_PAYMENT_MODULE_CODE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .locale(UPDATED_LOCALE)
            .channel(UPDATED_CHANNEL)
            .customerEmailAddress(UPDATED_CUSTOMER_EMAIL_ADDRESS)
            .orderType(UPDATED_ORDER_TYPE)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .currencyValue(UPDATED_CURRENCY_VALUE)
            .datePurchased(UPDATED_DATE_PURCHASED)
            .shippingModuleCode(UPDATED_SHIPPING_MODULE_CODE)
            .ipAddress(UPDATED_IP_ADDRESS)
            .customerAgreement(UPDATED_CUSTOMER_AGREEMENT);

        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesOrder)))
            .andExpect(status().isOk());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate);
        SalesOrder testSalesOrder = salesOrderList.get(salesOrderList.size() - 1);
        assertThat(testSalesOrder.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testSalesOrder.isConfirmedAddress()).isEqualTo(UPDATED_CONFIRMED_ADDRESS);
        assertThat(testSalesOrder.getOrderDateFinished()).isEqualTo(UPDATED_ORDER_DATE_FINISHED);
        assertThat(testSalesOrder.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testSalesOrder.getPaymentModuleCode()).isEqualTo(UPDATED_PAYMENT_MODULE_CODE);
        assertThat(testSalesOrder.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testSalesOrder.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testSalesOrder.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testSalesOrder.getCustomerEmailAddress()).isEqualTo(UPDATED_CUSTOMER_EMAIL_ADDRESS);
        assertThat(testSalesOrder.getOrderType()).isEqualTo(UPDATED_ORDER_TYPE);
        assertThat(testSalesOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSalesOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSalesOrder.getCurrencyValue()).isEqualTo(UPDATED_CURRENCY_VALUE);
        assertThat(testSalesOrder.getDatePurchased()).isEqualTo(UPDATED_DATE_PURCHASED);
        assertThat(testSalesOrder.getShippingModuleCode()).isEqualTo(UPDATED_SHIPPING_MODULE_CODE);
        assertThat(testSalesOrder.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
        assertThat(testSalesOrder.isCustomerAgreement()).isEqualTo(UPDATED_CUSTOMER_AGREEMENT);

        // Validate the SalesOrder in Elasticsearch
        SalesOrder salesOrderEs = salesOrderSearchRepository.findOne(testSalesOrder.getId());
        assertThat(salesOrderEs).isEqualToComparingFieldByField(testSalesOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesOrder() throws Exception {
        int databaseSizeBeforeUpdate = salesOrderRepository.findAll().size();

        // Create the SalesOrder

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalesOrderMockMvc.perform(put("/api/sales-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salesOrder)))
            .andExpect(status().isCreated());

        // Validate the SalesOrder in the database
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        int databaseSizeBeforeDelete = salesOrderRepository.findAll().size();

        // Get the salesOrder
        restSalesOrderMockMvc.perform(delete("/api/sales-orders/{id}", salesOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean salesOrderExistsInEs = salesOrderSearchRepository.exists(salesOrder.getId());
        assertThat(salesOrderExistsInEs).isFalse();

        // Validate the database is empty
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        assertThat(salesOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSalesOrder() throws Exception {
        // Initialize the database
        salesOrderService.save(salesOrder);

        // Search the salesOrder
        restSalesOrderMockMvc.perform(get("/api/_search/sales-orders?query=id:" + salesOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].confirmedAddress").value(hasItem(DEFAULT_CONFIRMED_ADDRESS.booleanValue())))
            .andExpect(jsonPath("$.[*].orderDateFinished").value(hasItem(DEFAULT_ORDER_DATE_FINISHED.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].paymentModuleCode").value(hasItem(DEFAULT_PAYMENT_MODULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].customerEmailAddress").value(hasItem(DEFAULT_CUSTOMER_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].orderType").value(hasItem(DEFAULT_ORDER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].currencyValue").value(hasItem(DEFAULT_CURRENCY_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].datePurchased").value(hasItem(DEFAULT_DATE_PURCHASED.toString())))
            .andExpect(jsonPath("$.[*].shippingModuleCode").value(hasItem(DEFAULT_SHIPPING_MODULE_CODE.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].customerAgreement").value(hasItem(DEFAULT_CUSTOMER_AGREEMENT.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalesOrder.class);
    }
}
