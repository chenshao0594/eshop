package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.repository.OrderTotalRepository;
import com.smartshop.eshop.service.OrderTotalService;
import com.smartshop.eshop.repository.search.OrderTotalSearchRepository;
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

import com.smartshop.eshop.domain.enumeration.OrderValueEnum;
import com.smartshop.eshop.domain.enumeration.OrderTotalEnum;
/**
 * Test class for the OrderTotalResource REST controller.
 *
 * @see OrderTotalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderTotalControllerIntTest {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String DEFAULT_ORDER_TOTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_TOTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final OrderValueEnum DEFAULT_ORDER_VALUE_TYPE = OrderValueEnum.ONE_TIME;
    private static final OrderValueEnum UPDATED_ORDER_VALUE_TYPE = OrderValueEnum.MONTHLY;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final OrderTotalEnum DEFAULT_ORDER_TOTAL_TYPE = OrderTotalEnum.SHIPPING;
    private static final OrderTotalEnum UPDATED_ORDER_TOTAL_TYPE = OrderTotalEnum.HANDLING;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    @Autowired
    private OrderTotalRepository orderTotalRepository;

    @Autowired
    private OrderTotalService orderTotalService;

    @Autowired
    private OrderTotalSearchRepository orderTotalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderTotalMockMvc;

    private OrderTotal orderTotal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderTotalController orderTotalController = new OrderTotalController(orderTotalService);
        this.restOrderTotalMockMvc = MockMvcBuilders.standaloneSetup(orderTotalController)
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
    public static OrderTotal createEntity(EntityManager em) {
        OrderTotal orderTotal = new OrderTotal()
            .value(DEFAULT_VALUE)
            .orderTotalCode(DEFAULT_ORDER_TOTAL_CODE)
            .text(DEFAULT_TEXT)
            .orderValueType(DEFAULT_ORDER_VALUE_TYPE)
            .sortOrder(DEFAULT_SORT_ORDER)
            .orderTotalType(DEFAULT_ORDER_TOTAL_TYPE)
            .title(DEFAULT_TITLE)
            .module(DEFAULT_MODULE);
        return orderTotal;
    }

    @Before
    public void initTest() {
        orderTotalSearchRepository.deleteAll();
        orderTotal = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTotal() throws Exception {
        int databaseSizeBeforeCreate = orderTotalRepository.findAll().size();

        // Create the OrderTotal
        restOrderTotalMockMvc.perform(post("/api/order-totals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTotal)))
            .andExpect(status().isCreated());

        // Validate the OrderTotal in the database
        List<OrderTotal> orderTotalList = orderTotalRepository.findAll();
        assertThat(orderTotalList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTotal testOrderTotal = orderTotalList.get(orderTotalList.size() - 1);
        assertThat(testOrderTotal.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOrderTotal.getOrderTotalCode()).isEqualTo(DEFAULT_ORDER_TOTAL_CODE);
        assertThat(testOrderTotal.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testOrderTotal.getOrderValueType()).isEqualTo(DEFAULT_ORDER_VALUE_TYPE);
        assertThat(testOrderTotal.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testOrderTotal.getOrderTotalType()).isEqualTo(DEFAULT_ORDER_TOTAL_TYPE);
        assertThat(testOrderTotal.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOrderTotal.getModule()).isEqualTo(DEFAULT_MODULE);

        // Validate the OrderTotal in Elasticsearch
        OrderTotal orderTotalEs = orderTotalSearchRepository.findOne(testOrderTotal.getId());
        assertThat(orderTotalEs).isEqualToComparingFieldByField(testOrderTotal);
    }

    @Test
    @Transactional
    public void createOrderTotalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTotalRepository.findAll().size();

        // Create the OrderTotal with an existing ID
        orderTotal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTotalMockMvc.perform(post("/api/order-totals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTotal)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderTotal> orderTotalList = orderTotalRepository.findAll();
        assertThat(orderTotalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderTotals() throws Exception {
        // Initialize the database
        orderTotalRepository.saveAndFlush(orderTotal);

        // Get all the orderTotalList
        restOrderTotalMockMvc.perform(get("/api/order-totals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTotal.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].orderTotalCode").value(hasItem(DEFAULT_ORDER_TOTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].orderValueType").value(hasItem(DEFAULT_ORDER_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].orderTotalType").value(hasItem(DEFAULT_ORDER_TOTAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())));
    }

    @Test
    @Transactional
    public void getOrderTotal() throws Exception {
        // Initialize the database
        orderTotalRepository.saveAndFlush(orderTotal);

        // Get the orderTotal
        restOrderTotalMockMvc.perform(get("/api/order-totals/{id}", orderTotal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderTotal.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.orderTotalCode").value(DEFAULT_ORDER_TOTAL_CODE.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.orderValueType").value(DEFAULT_ORDER_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.orderTotalType").value(DEFAULT_ORDER_TOTAL_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderTotal() throws Exception {
        // Get the orderTotal
        restOrderTotalMockMvc.perform(get("/api/order-totals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTotal() throws Exception {
        // Initialize the database
        orderTotalService.save(orderTotal);

        int databaseSizeBeforeUpdate = orderTotalRepository.findAll().size();

        // Update the orderTotal
        OrderTotal updatedOrderTotal = orderTotalRepository.findOne(orderTotal.getId());
        updatedOrderTotal
            .value(UPDATED_VALUE)
            .orderTotalCode(UPDATED_ORDER_TOTAL_CODE)
            .text(UPDATED_TEXT)
            .orderValueType(UPDATED_ORDER_VALUE_TYPE)
            .sortOrder(UPDATED_SORT_ORDER)
            .orderTotalType(UPDATED_ORDER_TOTAL_TYPE)
            .title(UPDATED_TITLE)
            .module(UPDATED_MODULE);

        restOrderTotalMockMvc.perform(put("/api/order-totals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTotal)))
            .andExpect(status().isOk());

        // Validate the OrderTotal in the database
        List<OrderTotal> orderTotalList = orderTotalRepository.findAll();
        assertThat(orderTotalList).hasSize(databaseSizeBeforeUpdate);
        OrderTotal testOrderTotal = orderTotalList.get(orderTotalList.size() - 1);
        assertThat(testOrderTotal.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOrderTotal.getOrderTotalCode()).isEqualTo(UPDATED_ORDER_TOTAL_CODE);
        assertThat(testOrderTotal.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testOrderTotal.getOrderValueType()).isEqualTo(UPDATED_ORDER_VALUE_TYPE);
        assertThat(testOrderTotal.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testOrderTotal.getOrderTotalType()).isEqualTo(UPDATED_ORDER_TOTAL_TYPE);
        assertThat(testOrderTotal.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOrderTotal.getModule()).isEqualTo(UPDATED_MODULE);

        // Validate the OrderTotal in Elasticsearch
        OrderTotal orderTotalEs = orderTotalSearchRepository.findOne(testOrderTotal.getId());
        assertThat(orderTotalEs).isEqualToComparingFieldByField(testOrderTotal);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTotal() throws Exception {
        int databaseSizeBeforeUpdate = orderTotalRepository.findAll().size();

        // Create the OrderTotal

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderTotalMockMvc.perform(put("/api/order-totals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderTotal)))
            .andExpect(status().isCreated());

        // Validate the OrderTotal in the database
        List<OrderTotal> orderTotalList = orderTotalRepository.findAll();
        assertThat(orderTotalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderTotal() throws Exception {
        // Initialize the database
        orderTotalService.save(orderTotal);

        int databaseSizeBeforeDelete = orderTotalRepository.findAll().size();

        // Get the orderTotal
        restOrderTotalMockMvc.perform(delete("/api/order-totals/{id}", orderTotal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderTotalExistsInEs = orderTotalSearchRepository.exists(orderTotal.getId());
        assertThat(orderTotalExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderTotal> orderTotalList = orderTotalRepository.findAll();
        assertThat(orderTotalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderTotal() throws Exception {
        // Initialize the database
        orderTotalService.save(orderTotal);

        // Search the orderTotal
        restOrderTotalMockMvc.perform(get("/api/_search/order-totals?query=id:" + orderTotal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTotal.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].orderTotalCode").value(hasItem(DEFAULT_ORDER_TOTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].orderValueType").value(hasItem(DEFAULT_ORDER_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].orderTotalType").value(hasItem(DEFAULT_ORDER_TOTAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTotal.class);
    }
}
