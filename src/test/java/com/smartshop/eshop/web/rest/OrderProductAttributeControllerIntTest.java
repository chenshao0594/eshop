package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderProductAttribute;
import com.smartshop.eshop.repository.OrderProductAttributeRepository;
import com.smartshop.eshop.service.OrderProductAttributeService;
import com.smartshop.eshop.repository.search.OrderProductAttributeSearchRepository;
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
 * Test class for the OrderProductAttributeResource REST controller.
 *
 * @see OrderProductAttributeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderProductAttributeControllerIntTest {

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_ATTRIBUTE_WEIGHT = new BigDecimal(2);

    private static final Long DEFAULT_PRODUCT_OPTION_VALUE_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_VALUE_ID = 2L;

    private static final BigDecimal DEFAULT_PRODUCT_ATTRIBUTE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_ATTRIBUTE_PRICE = new BigDecimal(2);

    private static final Boolean DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE = false;
    private static final Boolean UPDATED_PRODUCT_ATTRIBUTE_IS_FREE = true;

    private static final Long DEFAULT_PRODUCT_OPTION_ID = 1L;
    private static final Long UPDATED_PRODUCT_OPTION_ID = 2L;

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_VALUE_NAME = "BBBBBBBBBB";

    @Autowired
    private OrderProductAttributeRepository orderProductAttributeRepository;

    @Autowired
    private OrderProductAttributeService orderProductAttributeService;

    @Autowired
    private OrderProductAttributeSearchRepository orderProductAttributeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderProductAttributeMockMvc;

    private OrderProductAttribute orderProductAttribute;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderProductAttributeController orderProductAttributeController = new OrderProductAttributeController(orderProductAttributeService);
        this.restOrderProductAttributeMockMvc = MockMvcBuilders.standaloneSetup(orderProductAttributeController)
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
    public static OrderProductAttribute createEntity(EntityManager em) {
        OrderProductAttribute orderProductAttribute = new OrderProductAttribute()
            .productAttributeName(DEFAULT_PRODUCT_ATTRIBUTE_NAME)
            .productAttributeWeight(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT)
            .productOptionValueId(DEFAULT_PRODUCT_OPTION_VALUE_ID)
            .productAttributePrice(DEFAULT_PRODUCT_ATTRIBUTE_PRICE)
            .productAttributeIsFree(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE)
            .productOptionId(DEFAULT_PRODUCT_OPTION_ID)
            .productAttributeValueName(DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME);
        return orderProductAttribute;
    }

    @Before
    public void initTest() {
        orderProductAttributeSearchRepository.deleteAll();
        orderProductAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = orderProductAttributeRepository.findAll().size();

        // Create the OrderProductAttribute
        restOrderProductAttributeMockMvc.perform(post("/api/order-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductAttribute)))
            .andExpect(status().isCreated());

        // Validate the OrderProductAttribute in the database
        List<OrderProductAttribute> orderProductAttributeList = orderProductAttributeRepository.findAll();
        assertThat(orderProductAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProductAttribute testOrderProductAttribute = orderProductAttributeList.get(orderProductAttributeList.size() - 1);
        assertThat(testOrderProductAttribute.getProductAttributeName()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_NAME);
        assertThat(testOrderProductAttribute.getProductAttributeWeight()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT);
        assertThat(testOrderProductAttribute.getProductOptionValueId()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE_ID);
        assertThat(testOrderProductAttribute.getProductAttributePrice()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_PRICE);
        assertThat(testOrderProductAttribute.isProductAttributeIsFree()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE);
        assertThat(testOrderProductAttribute.getProductOptionId()).isEqualTo(DEFAULT_PRODUCT_OPTION_ID);
        assertThat(testOrderProductAttribute.getProductAttributeValueName()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME);

        // Validate the OrderProductAttribute in Elasticsearch
        OrderProductAttribute orderProductAttributeEs = orderProductAttributeSearchRepository.findOne(testOrderProductAttribute.getId());
        assertThat(orderProductAttributeEs).isEqualToComparingFieldByField(testOrderProductAttribute);
    }

    @Test
    @Transactional
    public void createOrderProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderProductAttributeRepository.findAll().size();

        // Create the OrderProductAttribute with an existing ID
        orderProductAttribute.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductAttributeMockMvc.perform(post("/api/order-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductAttribute)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderProductAttribute> orderProductAttributeList = orderProductAttributeRepository.findAll();
        assertThat(orderProductAttributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderProductAttributes() throws Exception {
        // Initialize the database
        orderProductAttributeRepository.saveAndFlush(orderProductAttribute);

        // Get all the orderProductAttributeList
        restOrderProductAttributeMockMvc.perform(get("/api/order-product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_NAME.toString())))
            .andExpect(jsonPath("$.[*].productAttributeWeight").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productOptionValueId").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributePrice").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeIsFree").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionId").value(hasItem(DEFAULT_PRODUCT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValueName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME.toString())));
    }

    @Test
    @Transactional
    public void getOrderProductAttribute() throws Exception {
        // Initialize the database
        orderProductAttributeRepository.saveAndFlush(orderProductAttribute);

        // Get the orderProductAttribute
        restOrderProductAttributeMockMvc.perform(get("/api/order-product-attributes/{id}", orderProductAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderProductAttribute.getId().intValue()))
            .andExpect(jsonPath("$.productAttributeName").value(DEFAULT_PRODUCT_ATTRIBUTE_NAME.toString()))
            .andExpect(jsonPath("$.productAttributeWeight").value(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue()))
            .andExpect(jsonPath("$.productOptionValueId").value(DEFAULT_PRODUCT_OPTION_VALUE_ID.intValue()))
            .andExpect(jsonPath("$.productAttributePrice").value(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue()))
            .andExpect(jsonPath("$.productAttributeIsFree").value(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue()))
            .andExpect(jsonPath("$.productOptionId").value(DEFAULT_PRODUCT_OPTION_ID.intValue()))
            .andExpect(jsonPath("$.productAttributeValueName").value(DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderProductAttribute() throws Exception {
        // Get the orderProductAttribute
        restOrderProductAttributeMockMvc.perform(get("/api/order-product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderProductAttribute() throws Exception {
        // Initialize the database
        orderProductAttributeService.save(orderProductAttribute);

        int databaseSizeBeforeUpdate = orderProductAttributeRepository.findAll().size();

        // Update the orderProductAttribute
        OrderProductAttribute updatedOrderProductAttribute = orderProductAttributeRepository.findOne(orderProductAttribute.getId());
        updatedOrderProductAttribute
            .productAttributeName(UPDATED_PRODUCT_ATTRIBUTE_NAME)
            .productAttributeWeight(UPDATED_PRODUCT_ATTRIBUTE_WEIGHT)
            .productOptionValueId(UPDATED_PRODUCT_OPTION_VALUE_ID)
            .productAttributePrice(UPDATED_PRODUCT_ATTRIBUTE_PRICE)
            .productAttributeIsFree(UPDATED_PRODUCT_ATTRIBUTE_IS_FREE)
            .productOptionId(UPDATED_PRODUCT_OPTION_ID)
            .productAttributeValueName(UPDATED_PRODUCT_ATTRIBUTE_VALUE_NAME);

        restOrderProductAttributeMockMvc.perform(put("/api/order-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderProductAttribute)))
            .andExpect(status().isOk());

        // Validate the OrderProductAttribute in the database
        List<OrderProductAttribute> orderProductAttributeList = orderProductAttributeRepository.findAll();
        assertThat(orderProductAttributeList).hasSize(databaseSizeBeforeUpdate);
        OrderProductAttribute testOrderProductAttribute = orderProductAttributeList.get(orderProductAttributeList.size() - 1);
        assertThat(testOrderProductAttribute.getProductAttributeName()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_NAME);
        assertThat(testOrderProductAttribute.getProductAttributeWeight()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_WEIGHT);
        assertThat(testOrderProductAttribute.getProductOptionValueId()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE_ID);
        assertThat(testOrderProductAttribute.getProductAttributePrice()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_PRICE);
        assertThat(testOrderProductAttribute.isProductAttributeIsFree()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_IS_FREE);
        assertThat(testOrderProductAttribute.getProductOptionId()).isEqualTo(UPDATED_PRODUCT_OPTION_ID);
        assertThat(testOrderProductAttribute.getProductAttributeValueName()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_VALUE_NAME);

        // Validate the OrderProductAttribute in Elasticsearch
        OrderProductAttribute orderProductAttributeEs = orderProductAttributeSearchRepository.findOne(testOrderProductAttribute.getId());
        assertThat(orderProductAttributeEs).isEqualToComparingFieldByField(testOrderProductAttribute);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = orderProductAttributeRepository.findAll().size();

        // Create the OrderProductAttribute

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderProductAttributeMockMvc.perform(put("/api/order-product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductAttribute)))
            .andExpect(status().isCreated());

        // Validate the OrderProductAttribute in the database
        List<OrderProductAttribute> orderProductAttributeList = orderProductAttributeRepository.findAll();
        assertThat(orderProductAttributeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderProductAttribute() throws Exception {
        // Initialize the database
        orderProductAttributeService.save(orderProductAttribute);

        int databaseSizeBeforeDelete = orderProductAttributeRepository.findAll().size();

        // Get the orderProductAttribute
        restOrderProductAttributeMockMvc.perform(delete("/api/order-product-attributes/{id}", orderProductAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderProductAttributeExistsInEs = orderProductAttributeSearchRepository.exists(orderProductAttribute.getId());
        assertThat(orderProductAttributeExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderProductAttribute> orderProductAttributeList = orderProductAttributeRepository.findAll();
        assertThat(orderProductAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderProductAttribute() throws Exception {
        // Initialize the database
        orderProductAttributeService.save(orderProductAttribute);

        // Search the orderProductAttribute
        restOrderProductAttributeMockMvc.perform(get("/api/_search/order-product-attributes?query=id:" + orderProductAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_NAME.toString())))
            .andExpect(jsonPath("$.[*].productAttributeWeight").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].productOptionValueId").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributePrice").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeIsFree").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_IS_FREE.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionId").value(hasItem(DEFAULT_PRODUCT_OPTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValueName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProductAttribute.class);
    }
}
