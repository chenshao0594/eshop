package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderAccount;
import com.smartshop.eshop.repository.OrderAccountRepository;
import com.smartshop.eshop.service.OrderAccountService;
import com.smartshop.eshop.repository.search.OrderAccountSearchRepository;
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
 * Test class for the OrderAccountResource REST controller.
 *
 * @see OrderAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderAccountControllerIntTest {

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ORDER_ACCOUNT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_ACCOUNT_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ORDER_ACCOUNT_BILL_DAY = 1;
    private static final Integer UPDATED_ORDER_ACCOUNT_BILL_DAY = 2;

    @Autowired
    private OrderAccountRepository orderAccountRepository;

    @Autowired
    private OrderAccountService orderAccountService;

    @Autowired
    private OrderAccountSearchRepository orderAccountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderAccountMockMvc;

    private OrderAccount orderAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderAccountController orderAccountController = new OrderAccountController(orderAccountService);
        this.restOrderAccountMockMvc = MockMvcBuilders.standaloneSetup(orderAccountController)
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
    public static OrderAccount createEntity(EntityManager em) {
        OrderAccount orderAccount = new OrderAccount()
            .orderAccountStartDate(DEFAULT_ORDER_ACCOUNT_START_DATE)
            .orderAccountEndDate(DEFAULT_ORDER_ACCOUNT_END_DATE)
            .orderAccountBillDay(DEFAULT_ORDER_ACCOUNT_BILL_DAY);
        return orderAccount;
    }

    @Before
    public void initTest() {
        orderAccountSearchRepository.deleteAll();
        orderAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderAccount() throws Exception {
        int databaseSizeBeforeCreate = orderAccountRepository.findAll().size();

        // Create the OrderAccount
        restOrderAccountMockMvc.perform(post("/api/order-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccount)))
            .andExpect(status().isCreated());

        // Validate the OrderAccount in the database
        List<OrderAccount> orderAccountList = orderAccountRepository.findAll();
        assertThat(orderAccountList).hasSize(databaseSizeBeforeCreate + 1);
        OrderAccount testOrderAccount = orderAccountList.get(orderAccountList.size() - 1);
        assertThat(testOrderAccount.getOrderAccountStartDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_START_DATE);
        assertThat(testOrderAccount.getOrderAccountEndDate()).isEqualTo(DEFAULT_ORDER_ACCOUNT_END_DATE);
        assertThat(testOrderAccount.getOrderAccountBillDay()).isEqualTo(DEFAULT_ORDER_ACCOUNT_BILL_DAY);

        // Validate the OrderAccount in Elasticsearch
        OrderAccount orderAccountEs = orderAccountSearchRepository.findOne(testOrderAccount.getId());
        assertThat(orderAccountEs).isEqualToComparingFieldByField(testOrderAccount);
    }

    @Test
    @Transactional
    public void createOrderAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderAccountRepository.findAll().size();

        // Create the OrderAccount with an existing ID
        orderAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderAccountMockMvc.perform(post("/api/order-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderAccount> orderAccountList = orderAccountRepository.findAll();
        assertThat(orderAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderAccounts() throws Exception {
        // Initialize the database
        orderAccountRepository.saveAndFlush(orderAccount);

        // Get all the orderAccountList
        restOrderAccountMockMvc.perform(get("/api/order-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderAccountStartDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountEndDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountBillDay").value(hasItem(DEFAULT_ORDER_ACCOUNT_BILL_DAY)));
    }

    @Test
    @Transactional
    public void getOrderAccount() throws Exception {
        // Initialize the database
        orderAccountRepository.saveAndFlush(orderAccount);

        // Get the orderAccount
        restOrderAccountMockMvc.perform(get("/api/order-accounts/{id}", orderAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderAccount.getId().intValue()))
            .andExpect(jsonPath("$.orderAccountStartDate").value(DEFAULT_ORDER_ACCOUNT_START_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountEndDate").value(DEFAULT_ORDER_ACCOUNT_END_DATE.toString()))
            .andExpect(jsonPath("$.orderAccountBillDay").value(DEFAULT_ORDER_ACCOUNT_BILL_DAY));
    }

    @Test
    @Transactional
    public void getNonExistingOrderAccount() throws Exception {
        // Get the orderAccount
        restOrderAccountMockMvc.perform(get("/api/order-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderAccount() throws Exception {
        // Initialize the database
        orderAccountService.save(orderAccount);

        int databaseSizeBeforeUpdate = orderAccountRepository.findAll().size();

        // Update the orderAccount
        OrderAccount updatedOrderAccount = orderAccountRepository.findOne(orderAccount.getId());
        updatedOrderAccount
            .orderAccountStartDate(UPDATED_ORDER_ACCOUNT_START_DATE)
            .orderAccountEndDate(UPDATED_ORDER_ACCOUNT_END_DATE)
            .orderAccountBillDay(UPDATED_ORDER_ACCOUNT_BILL_DAY);

        restOrderAccountMockMvc.perform(put("/api/order-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderAccount)))
            .andExpect(status().isOk());

        // Validate the OrderAccount in the database
        List<OrderAccount> orderAccountList = orderAccountRepository.findAll();
        assertThat(orderAccountList).hasSize(databaseSizeBeforeUpdate);
        OrderAccount testOrderAccount = orderAccountList.get(orderAccountList.size() - 1);
        assertThat(testOrderAccount.getOrderAccountStartDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_START_DATE);
        assertThat(testOrderAccount.getOrderAccountEndDate()).isEqualTo(UPDATED_ORDER_ACCOUNT_END_DATE);
        assertThat(testOrderAccount.getOrderAccountBillDay()).isEqualTo(UPDATED_ORDER_ACCOUNT_BILL_DAY);

        // Validate the OrderAccount in Elasticsearch
        OrderAccount orderAccountEs = orderAccountSearchRepository.findOne(testOrderAccount.getId());
        assertThat(orderAccountEs).isEqualToComparingFieldByField(testOrderAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderAccount() throws Exception {
        int databaseSizeBeforeUpdate = orderAccountRepository.findAll().size();

        // Create the OrderAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderAccountMockMvc.perform(put("/api/order-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderAccount)))
            .andExpect(status().isCreated());

        // Validate the OrderAccount in the database
        List<OrderAccount> orderAccountList = orderAccountRepository.findAll();
        assertThat(orderAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderAccount() throws Exception {
        // Initialize the database
        orderAccountService.save(orderAccount);

        int databaseSizeBeforeDelete = orderAccountRepository.findAll().size();

        // Get the orderAccount
        restOrderAccountMockMvc.perform(delete("/api/order-accounts/{id}", orderAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderAccountExistsInEs = orderAccountSearchRepository.exists(orderAccount.getId());
        assertThat(orderAccountExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderAccount> orderAccountList = orderAccountRepository.findAll();
        assertThat(orderAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderAccount() throws Exception {
        // Initialize the database
        orderAccountService.save(orderAccount);

        // Search the orderAccount
        restOrderAccountMockMvc.perform(get("/api/_search/order-accounts?query=id:" + orderAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderAccountStartDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountEndDate").value(hasItem(DEFAULT_ORDER_ACCOUNT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAccountBillDay").value(hasItem(DEFAULT_ORDER_ACCOUNT_BILL_DAY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderAccount.class);
    }
}
