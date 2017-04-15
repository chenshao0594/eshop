package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.repository.ShoppingCartRepository;
import com.smartshop.eshop.service.ShoppingCartService;
import com.smartshop.eshop.repository.search.ShoppingCartSearchRepository;
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
 * Test class for the ShoppingCartResource REST controller.
 *
 * @see ShoppingCartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ShoppingCartControllerIntTest {

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String DEFAULT_SHOPPING_CART_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SHOPPING_CART_CODE = "BBBBBBBBBB";

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartSearchRepository shoppingCartSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingCartMockMvc;

    private ShoppingCart shoppingCart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShoppingCartController shoppingCartController = new ShoppingCartController(shoppingCartService);
        this.restShoppingCartMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
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
    public static ShoppingCart createEntity(EntityManager em) {
        ShoppingCart shoppingCart = new ShoppingCart()
            .customerId(DEFAULT_CUSTOMER_ID)
            .shoppingCartCode(DEFAULT_SHOPPING_CART_CODE);
        return shoppingCart;
    }

    @Before
    public void initTest() {
        shoppingCartSearchRepository.deleteAll();
        shoppingCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCart() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testShoppingCart.getShoppingCartCode()).isEqualTo(DEFAULT_SHOPPING_CART_CODE);

        // Validate the ShoppingCart in Elasticsearch
        ShoppingCart shoppingCartEs = shoppingCartSearchRepository.findOne(testShoppingCart.getId());
        assertThat(shoppingCartEs).isEqualToComparingFieldByField(testShoppingCart);
    }

    @Test
    @Transactional
    public void createShoppingCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart with an existing ID
        shoppingCart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartMockMvc.perform(post("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCarts() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get all the shoppingCartList
        restShoppingCartMockMvc.perform(get("/api/shopping-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartCode").value(hasItem(DEFAULT_SHOPPING_CART_CODE.toString())));
    }

    @Test
    @Transactional
    public void getShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartRepository.saveAndFlush(shoppingCart);

        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCart.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.shoppingCartCode").value(DEFAULT_SHOPPING_CART_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCart() throws Exception {
        // Get the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/shopping-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartService.save(shoppingCart);

        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Update the shoppingCart
        ShoppingCart updatedShoppingCart = shoppingCartRepository.findOne(shoppingCart.getId());
        updatedShoppingCart
            .customerId(UPDATED_CUSTOMER_ID)
            .shoppingCartCode(UPDATED_SHOPPING_CART_CODE);

        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShoppingCart)))
            .andExpect(status().isOk());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCart testShoppingCart = shoppingCartList.get(shoppingCartList.size() - 1);
        assertThat(testShoppingCart.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testShoppingCart.getShoppingCartCode()).isEqualTo(UPDATED_SHOPPING_CART_CODE);

        // Validate the ShoppingCart in Elasticsearch
        ShoppingCart shoppingCartEs = shoppingCartSearchRepository.findOne(testShoppingCart.getId());
        assertThat(shoppingCartEs).isEqualToComparingFieldByField(testShoppingCart);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCart() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartRepository.findAll().size();

        // Create the ShoppingCart

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingCartMockMvc.perform(put("/api/shopping-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCart)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCart in the database
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartService.save(shoppingCart);

        int databaseSizeBeforeDelete = shoppingCartRepository.findAll().size();

        // Get the shoppingCart
        restShoppingCartMockMvc.perform(delete("/api/shopping-carts/{id}", shoppingCart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shoppingCartExistsInEs = shoppingCartSearchRepository.exists(shoppingCart.getId());
        assertThat(shoppingCartExistsInEs).isFalse();

        // Validate the database is empty
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAll();
        assertThat(shoppingCartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShoppingCart() throws Exception {
        // Initialize the database
        shoppingCartService.save(shoppingCart);

        // Search the shoppingCart
        restShoppingCartMockMvc.perform(get("/api/_search/shopping-carts?query=id:" + shoppingCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].shoppingCartCode").value(hasItem(DEFAULT_SHOPPING_CART_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCart.class);
    }
}
