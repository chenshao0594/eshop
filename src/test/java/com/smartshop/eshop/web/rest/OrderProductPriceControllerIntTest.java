package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderProductPrice;
import com.smartshop.eshop.repository.OrderProductPriceRepository;
import com.smartshop.eshop.service.OrderProductPriceService;
import com.smartshop.eshop.repository.search.OrderProductPriceSearchRepository;
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
 * Test class for the OrderProductPriceResource REST controller.
 *
 * @see OrderProductPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderProductPriceControllerIntTest {

    private static final BigDecimal DEFAULT_PRODUCT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE = new BigDecimal(2);

    private static final String DEFAULT_PRODUCT_PRICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PRICE_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_PRICE_SPECIAL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRODUCT_PRICE_SPECIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE_SPECIAL = new BigDecimal(2);

    private static final LocalDate DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_PRICE_SPECIAL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PRODUCT_PRICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_PRICE_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_PRICE = false;
    private static final Boolean UPDATED_DEFAULT_PRICE = true;

    @Autowired
    private OrderProductPriceRepository orderProductPriceRepository;

    @Autowired
    private OrderProductPriceService orderProductPriceService;

    @Autowired
    private OrderProductPriceSearchRepository orderProductPriceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderProductPriceMockMvc;

    private OrderProductPrice orderProductPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderProductPriceController orderProductPriceController = new OrderProductPriceController(orderProductPriceService);
        this.restOrderProductPriceMockMvc = MockMvcBuilders.standaloneSetup(orderProductPriceController)
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
    public static OrderProductPrice createEntity(EntityManager em) {
        OrderProductPrice orderProductPrice = new OrderProductPrice()
            .productPrice(DEFAULT_PRODUCT_PRICE)
            .productPriceCode(DEFAULT_PRODUCT_PRICE_CODE)
            .productPriceSpecialStartDate(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE)
            .productPriceSpecial(DEFAULT_PRODUCT_PRICE_SPECIAL)
            .productPriceSpecialEndDate(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE)
            .productPriceName(DEFAULT_PRODUCT_PRICE_NAME)
            .defaultPrice(DEFAULT_DEFAULT_PRICE);
        return orderProductPrice;
    }

    @Before
    public void initTest() {
        orderProductPriceSearchRepository.deleteAll();
        orderProductPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderProductPrice() throws Exception {
        int databaseSizeBeforeCreate = orderProductPriceRepository.findAll().size();

        // Create the OrderProductPrice
        restOrderProductPriceMockMvc.perform(post("/api/order-product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductPrice)))
            .andExpect(status().isCreated());

        // Validate the OrderProductPrice in the database
        List<OrderProductPrice> orderProductPriceList = orderProductPriceRepository.findAll();
        assertThat(orderProductPriceList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProductPrice testOrderProductPrice = orderProductPriceList.get(orderProductPriceList.size() - 1);
        assertThat(testOrderProductPrice.getProductPrice()).isEqualTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testOrderProductPrice.getProductPriceCode()).isEqualTo(DEFAULT_PRODUCT_PRICE_CODE);
        assertThat(testOrderProductPrice.getProductPriceSpecialStartDate()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE);
        assertThat(testOrderProductPrice.getProductPriceSpecial()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL);
        assertThat(testOrderProductPrice.getProductPriceSpecialEndDate()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE);
        assertThat(testOrderProductPrice.getProductPriceName()).isEqualTo(DEFAULT_PRODUCT_PRICE_NAME);
        assertThat(testOrderProductPrice.isDefaultPrice()).isEqualTo(DEFAULT_DEFAULT_PRICE);

        // Validate the OrderProductPrice in Elasticsearch
        OrderProductPrice orderProductPriceEs = orderProductPriceSearchRepository.findOne(testOrderProductPrice.getId());
        assertThat(orderProductPriceEs).isEqualToComparingFieldByField(testOrderProductPrice);
    }

    @Test
    @Transactional
    public void createOrderProductPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderProductPriceRepository.findAll().size();

        // Create the OrderProductPrice with an existing ID
        orderProductPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductPriceMockMvc.perform(post("/api/order-product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductPrice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderProductPrice> orderProductPriceList = orderProductPriceRepository.findAll();
        assertThat(orderProductPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderProductPrices() throws Exception {
        // Initialize the database
        orderProductPriceRepository.saveAndFlush(orderProductPrice);

        // Get all the orderProductPriceList
        restOrderProductPriceMockMvc.perform(get("/api/order-product-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(DEFAULT_PRODUCT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productPriceCode").value(hasItem(DEFAULT_PRODUCT_PRICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialStartDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecial").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL.intValue())))
            .andExpect(jsonPath("$.[*].productPriceSpecialEndDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceName").value(hasItem(DEFAULT_PRODUCT_PRICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultPrice").value(hasItem(DEFAULT_DEFAULT_PRICE.booleanValue())));
    }

    @Test
    @Transactional
    public void getOrderProductPrice() throws Exception {
        // Initialize the database
        orderProductPriceRepository.saveAndFlush(orderProductPrice);

        // Get the orderProductPrice
        restOrderProductPriceMockMvc.perform(get("/api/order-product-prices/{id}", orderProductPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderProductPrice.getId().intValue()))
            .andExpect(jsonPath("$.productPrice").value(DEFAULT_PRODUCT_PRICE.intValue()))
            .andExpect(jsonPath("$.productPriceCode").value(DEFAULT_PRODUCT_PRICE_CODE.toString()))
            .andExpect(jsonPath("$.productPriceSpecialStartDate").value(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString()))
            .andExpect(jsonPath("$.productPriceSpecial").value(DEFAULT_PRODUCT_PRICE_SPECIAL.intValue()))
            .andExpect(jsonPath("$.productPriceSpecialEndDate").value(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString()))
            .andExpect(jsonPath("$.productPriceName").value(DEFAULT_PRODUCT_PRICE_NAME.toString()))
            .andExpect(jsonPath("$.defaultPrice").value(DEFAULT_DEFAULT_PRICE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderProductPrice() throws Exception {
        // Get the orderProductPrice
        restOrderProductPriceMockMvc.perform(get("/api/order-product-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderProductPrice() throws Exception {
        // Initialize the database
        orderProductPriceService.save(orderProductPrice);

        int databaseSizeBeforeUpdate = orderProductPriceRepository.findAll().size();

        // Update the orderProductPrice
        OrderProductPrice updatedOrderProductPrice = orderProductPriceRepository.findOne(orderProductPrice.getId());
        updatedOrderProductPrice
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productPriceCode(UPDATED_PRODUCT_PRICE_CODE)
            .productPriceSpecialStartDate(UPDATED_PRODUCT_PRICE_SPECIAL_START_DATE)
            .productPriceSpecial(UPDATED_PRODUCT_PRICE_SPECIAL)
            .productPriceSpecialEndDate(UPDATED_PRODUCT_PRICE_SPECIAL_END_DATE)
            .productPriceName(UPDATED_PRODUCT_PRICE_NAME)
            .defaultPrice(UPDATED_DEFAULT_PRICE);

        restOrderProductPriceMockMvc.perform(put("/api/order-product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderProductPrice)))
            .andExpect(status().isOk());

        // Validate the OrderProductPrice in the database
        List<OrderProductPrice> orderProductPriceList = orderProductPriceRepository.findAll();
        assertThat(orderProductPriceList).hasSize(databaseSizeBeforeUpdate);
        OrderProductPrice testOrderProductPrice = orderProductPriceList.get(orderProductPriceList.size() - 1);
        assertThat(testOrderProductPrice.getProductPrice()).isEqualTo(UPDATED_PRODUCT_PRICE);
        assertThat(testOrderProductPrice.getProductPriceCode()).isEqualTo(UPDATED_PRODUCT_PRICE_CODE);
        assertThat(testOrderProductPrice.getProductPriceSpecialStartDate()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL_START_DATE);
        assertThat(testOrderProductPrice.getProductPriceSpecial()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL);
        assertThat(testOrderProductPrice.getProductPriceSpecialEndDate()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL_END_DATE);
        assertThat(testOrderProductPrice.getProductPriceName()).isEqualTo(UPDATED_PRODUCT_PRICE_NAME);
        assertThat(testOrderProductPrice.isDefaultPrice()).isEqualTo(UPDATED_DEFAULT_PRICE);

        // Validate the OrderProductPrice in Elasticsearch
        OrderProductPrice orderProductPriceEs = orderProductPriceSearchRepository.findOne(testOrderProductPrice.getId());
        assertThat(orderProductPriceEs).isEqualToComparingFieldByField(testOrderProductPrice);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderProductPrice() throws Exception {
        int databaseSizeBeforeUpdate = orderProductPriceRepository.findAll().size();

        // Create the OrderProductPrice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderProductPriceMockMvc.perform(put("/api/order-product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductPrice)))
            .andExpect(status().isCreated());

        // Validate the OrderProductPrice in the database
        List<OrderProductPrice> orderProductPriceList = orderProductPriceRepository.findAll();
        assertThat(orderProductPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderProductPrice() throws Exception {
        // Initialize the database
        orderProductPriceService.save(orderProductPrice);

        int databaseSizeBeforeDelete = orderProductPriceRepository.findAll().size();

        // Get the orderProductPrice
        restOrderProductPriceMockMvc.perform(delete("/api/order-product-prices/{id}", orderProductPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderProductPriceExistsInEs = orderProductPriceSearchRepository.exists(orderProductPrice.getId());
        assertThat(orderProductPriceExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderProductPrice> orderProductPriceList = orderProductPriceRepository.findAll();
        assertThat(orderProductPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderProductPrice() throws Exception {
        // Initialize the database
        orderProductPriceService.save(orderProductPrice);

        // Search the orderProductPrice
        restOrderProductPriceMockMvc.perform(get("/api/_search/order-product-prices?query=id:" + orderProductPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(DEFAULT_PRODUCT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productPriceCode").value(hasItem(DEFAULT_PRODUCT_PRICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialStartDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecial").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL.intValue())))
            .andExpect(jsonPath("$.[*].productPriceSpecialEndDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceName").value(hasItem(DEFAULT_PRODUCT_PRICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultPrice").value(hasItem(DEFAULT_DEFAULT_PRICE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProductPrice.class);
    }
}
