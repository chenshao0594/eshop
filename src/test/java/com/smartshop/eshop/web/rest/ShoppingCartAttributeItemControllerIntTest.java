package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import com.smartshop.eshop.repository.ShoppingCartAttributeItemRepository;
import com.smartshop.eshop.service.ShoppingCartAttributeItemService;
import com.smartshop.eshop.repository.search.ShoppingCartAttributeItemSearchRepository;
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
 * Test class for the ShoppingCartAttributeItemResource REST controller.
 *
 * @see ShoppingCartAttributeItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ShoppingCartAttributeItemControllerIntTest {

    private static final Long DEFAULT_PRODUCT_ATTRIBUTE_ID = 1L;
    private static final Long UPDATED_PRODUCT_ATTRIBUTE_ID = 2L;

    @Autowired
    private ShoppingCartAttributeItemRepository shoppingCartAttributeItemRepository;

    @Autowired
    private ShoppingCartAttributeItemService shoppingCartAttributeItemService;

    @Autowired
    private ShoppingCartAttributeItemSearchRepository shoppingCartAttributeItemSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShoppingCartAttributeItemMockMvc;

    private ShoppingCartAttributeItem shoppingCartAttributeItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShoppingCartAttributeItemController shoppingCartAttributeItemController = new ShoppingCartAttributeItemController(shoppingCartAttributeItemService);
        this.restShoppingCartAttributeItemMockMvc = MockMvcBuilders.standaloneSetup(shoppingCartAttributeItemController)
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
    public static ShoppingCartAttributeItem createEntity(EntityManager em) {
        ShoppingCartAttributeItem shoppingCartAttributeItem = new ShoppingCartAttributeItem()
            .productAttributeId(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        return shoppingCartAttributeItem;
    }

    @Before
    public void initTest() {
        shoppingCartAttributeItemSearchRepository.deleteAll();
        shoppingCartAttributeItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createShoppingCartAttributeItem() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartAttributeItemRepository.findAll().size();

        // Create the ShoppingCartAttributeItem
        restShoppingCartAttributeItemMockMvc.perform(post("/api/shopping-cart-attribute-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartAttributeItem)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartAttributeItem in the database
        List<ShoppingCartAttributeItem> shoppingCartAttributeItemList = shoppingCartAttributeItemRepository.findAll();
        assertThat(shoppingCartAttributeItemList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingCartAttributeItem testShoppingCartAttributeItem = shoppingCartAttributeItemList.get(shoppingCartAttributeItemList.size() - 1);
        assertThat(testShoppingCartAttributeItem.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);

        // Validate the ShoppingCartAttributeItem in Elasticsearch
        ShoppingCartAttributeItem shoppingCartAttributeItemEs = shoppingCartAttributeItemSearchRepository.findOne(testShoppingCartAttributeItem.getId());
        assertThat(shoppingCartAttributeItemEs).isEqualToComparingFieldByField(testShoppingCartAttributeItem);
    }

    @Test
    @Transactional
    public void createShoppingCartAttributeItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shoppingCartAttributeItemRepository.findAll().size();

        // Create the ShoppingCartAttributeItem with an existing ID
        shoppingCartAttributeItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingCartAttributeItemMockMvc.perform(post("/api/shopping-cart-attribute-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartAttributeItem)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ShoppingCartAttributeItem> shoppingCartAttributeItemList = shoppingCartAttributeItemRepository.findAll();
        assertThat(shoppingCartAttributeItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShoppingCartAttributeItems() throws Exception {
        // Initialize the database
        shoppingCartAttributeItemRepository.saveAndFlush(shoppingCartAttributeItem);

        // Get all the shoppingCartAttributeItemList
        restShoppingCartAttributeItemMockMvc.perform(get("/api/shopping-cart-attribute-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartAttributeItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())));
    }

    @Test
    @Transactional
    public void getShoppingCartAttributeItem() throws Exception {
        // Initialize the database
        shoppingCartAttributeItemRepository.saveAndFlush(shoppingCartAttributeItem);

        // Get the shoppingCartAttributeItem
        restShoppingCartAttributeItemMockMvc.perform(get("/api/shopping-cart-attribute-items/{id}", shoppingCartAttributeItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingCartAttributeItem.getId().intValue()))
            .andExpect(jsonPath("$.productAttributeId").value(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShoppingCartAttributeItem() throws Exception {
        // Get the shoppingCartAttributeItem
        restShoppingCartAttributeItemMockMvc.perform(get("/api/shopping-cart-attribute-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShoppingCartAttributeItem() throws Exception {
        // Initialize the database
        shoppingCartAttributeItemService.save(shoppingCartAttributeItem);

        int databaseSizeBeforeUpdate = shoppingCartAttributeItemRepository.findAll().size();

        // Update the shoppingCartAttributeItem
        ShoppingCartAttributeItem updatedShoppingCartAttributeItem = shoppingCartAttributeItemRepository.findOne(shoppingCartAttributeItem.getId());
        updatedShoppingCartAttributeItem
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID);

        restShoppingCartAttributeItemMockMvc.perform(put("/api/shopping-cart-attribute-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShoppingCartAttributeItem)))
            .andExpect(status().isOk());

        // Validate the ShoppingCartAttributeItem in the database
        List<ShoppingCartAttributeItem> shoppingCartAttributeItemList = shoppingCartAttributeItemRepository.findAll();
        assertThat(shoppingCartAttributeItemList).hasSize(databaseSizeBeforeUpdate);
        ShoppingCartAttributeItem testShoppingCartAttributeItem = shoppingCartAttributeItemList.get(shoppingCartAttributeItemList.size() - 1);
        assertThat(testShoppingCartAttributeItem.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);

        // Validate the ShoppingCartAttributeItem in Elasticsearch
        ShoppingCartAttributeItem shoppingCartAttributeItemEs = shoppingCartAttributeItemSearchRepository.findOne(testShoppingCartAttributeItem.getId());
        assertThat(shoppingCartAttributeItemEs).isEqualToComparingFieldByField(testShoppingCartAttributeItem);
    }

    @Test
    @Transactional
    public void updateNonExistingShoppingCartAttributeItem() throws Exception {
        int databaseSizeBeforeUpdate = shoppingCartAttributeItemRepository.findAll().size();

        // Create the ShoppingCartAttributeItem

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShoppingCartAttributeItemMockMvc.perform(put("/api/shopping-cart-attribute-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shoppingCartAttributeItem)))
            .andExpect(status().isCreated());

        // Validate the ShoppingCartAttributeItem in the database
        List<ShoppingCartAttributeItem> shoppingCartAttributeItemList = shoppingCartAttributeItemRepository.findAll();
        assertThat(shoppingCartAttributeItemList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteShoppingCartAttributeItem() throws Exception {
        // Initialize the database
        shoppingCartAttributeItemService.save(shoppingCartAttributeItem);

        int databaseSizeBeforeDelete = shoppingCartAttributeItemRepository.findAll().size();

        // Get the shoppingCartAttributeItem
        restShoppingCartAttributeItemMockMvc.perform(delete("/api/shopping-cart-attribute-items/{id}", shoppingCartAttributeItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean shoppingCartAttributeItemExistsInEs = shoppingCartAttributeItemSearchRepository.exists(shoppingCartAttributeItem.getId());
        assertThat(shoppingCartAttributeItemExistsInEs).isFalse();

        // Validate the database is empty
        List<ShoppingCartAttributeItem> shoppingCartAttributeItemList = shoppingCartAttributeItemRepository.findAll();
        assertThat(shoppingCartAttributeItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchShoppingCartAttributeItem() throws Exception {
        // Initialize the database
        shoppingCartAttributeItemService.save(shoppingCartAttributeItem);

        // Search the shoppingCartAttributeItem
        restShoppingCartAttributeItemMockMvc.perform(get("/api/_search/shopping-cart-attribute-items?query=id:" + shoppingCartAttributeItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingCartAttributeItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingCartAttributeItem.class);
    }
}
