package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.repository.ShoppingCartItemRepository;
import com.smartshop.eshop.service.ShoppingCartItemService;
import com.smartshop.eshop.repository.search.ShoppingCartItemSearchRepository;
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
 * Test class for the ShoppingCartItemResource REST controller.
 *
 * @see ShoppingCartItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ShoppingCartItemControllerIntTest {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    private ShoppingCartItemSearchRepository shoppingCartItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingCartItemMockMvc;

    private ShoppingCartItem shoppingCartItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShoppingCartItemController shoppingCartItemController = new ShoppingCartItemController(shoppingCartItemService);
        this.restShoppingCartItemMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartItemController)
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
    public static ShoppingCartItem createEntity(EntityManager em) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem()
            .productId(DEFAULT_PRODUCT_ID)
            .quantity(DEFAULT_QUANTITY);
        return shoppingCartItem;
    }

    @Before
    public void initTest() {
        shoppingCartItemSearchRepository.deleteAll();
        shoppingCartItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCartItem() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem
        restShoppingCartItemMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItem)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCartItem testShoppingCartItem = shoppingCartItemList.get(shoppingCartItemList.size() - 1);
        assertThat(testShoppingCartItem.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testShoppingCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);

        // Validate the ShoppingCartItem in Elasticsearch
        ShoppingCartItem shoppingCartItemEs = shoppingCartItemSearchRepository.findOne(testShoppingCartItem.getId());
        assertThat(shoppingCartItemEs).isEqualToComparingFieldByField(testShoppingCartItem);
    }

    @Test
    @Transactional
    public void createShoppingCartItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem with an existing ID
        shoppingCartItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartItemMockMvc.perform(post("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCartItems() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        // Get all the shoppingCartItemList
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void getShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemRepository.saveAndFlush(shoppingCartItem);

        // Get the shoppingCartItem
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items/{id}", shoppingCartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCartItem.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCartItem() throws Exception {
        // Get the shoppingCartItem
        restShoppingCartItemMockMvc.perform(get("/api/shopping-cart-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemService.save(shoppingCartItem);

        int databaseSizeBeforeUpdate = shoppingCartItemRepository.findAll().size();

        // Update the shoppingCartItem
        ShoppingCartItem updatedShoppingCartItem = shoppingCartItemRepository.findOne(shoppingCartItem.getId());
        updatedShoppingCartItem
            .productId(UPDATED_PRODUCT_ID)
            .quantity(UPDATED_QUANTITY);

        restShoppingCartItemMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShoppingCartItem)))
            .andExpect(status().isOk());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCartItem testShoppingCartItem = shoppingCartItemList.get(shoppingCartItemList.size() - 1);
        assertThat(testShoppingCartItem.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testShoppingCartItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);

        // Validate the ShoppingCartItem in Elasticsearch
        ShoppingCartItem shoppingCartItemEs = shoppingCartItemSearchRepository.findOne(testShoppingCartItem.getId());
        assertThat(shoppingCartItemEs).isEqualToComparingFieldByField(testShoppingCartItem);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCartItem() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartItemRepository.findAll().size();

        // Create the ShoppingCartItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingCartItemMockMvc.perform(put("/api/shopping-cart-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartItem)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartItem in the database
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemService.save(shoppingCartItem);

        int databaseSizeBeforeDelete = shoppingCartItemRepository.findAll().size();

        // Get the shoppingCartItem
        restShoppingCartItemMockMvc.perform(delete("/api/shopping-cart-items/{id}", shoppingCartItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shoppingCartItemExistsInEs = shoppingCartItemSearchRepository.exists(shoppingCartItem.getId());
        assertThat(shoppingCartItemExistsInEs).isFalse();

        // Validate the database is empty
        List<ShoppingCartItem> shoppingCartItemList = shoppingCartItemRepository.findAll();
        assertThat(shoppingCartItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShoppingCartItem() throws Exception {
        // Initialize the database
        shoppingCartItemService.save(shoppingCartItem);

        // Search the shoppingCartItem
        restShoppingCartItemMockMvc.perform(get("/api/_search/shopping-cart-items?query=id:" + shoppingCartItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartItem.class);
    }
}
