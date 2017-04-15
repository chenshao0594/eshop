package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.OrderProductDownload;
import com.smartshop.eshop.repository.OrderProductDownloadRepository;
import com.smartshop.eshop.service.OrderProductDownloadService;
import com.smartshop.eshop.repository.search.OrderProductDownloadSearchRepository;
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
 * Test class for the OrderProductDownloadResource REST controller.
 *
 * @see OrderProductDownloadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class OrderProductDownloadControllerIntTest {

    private static final Integer DEFAULT_MAXDAYS = 1;
    private static final Integer UPDATED_MAXDAYS = 2;

    private static final Integer DEFAULT_DOWNLOAD_COUNT = 1;
    private static final Integer UPDATED_DOWNLOAD_COUNT = 2;

    private static final String DEFAULT_ORDER_PRODUCT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_PRODUCT_FILENAME = "BBBBBBBBBB";

    @Autowired
    private OrderProductDownloadRepository orderProductDownloadRepository;

    @Autowired
    private OrderProductDownloadService orderProductDownloadService;

    @Autowired
    private OrderProductDownloadSearchRepository orderProductDownloadSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderProductDownloadMockMvc;

    private OrderProductDownload orderProductDownload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderProductDownloadController orderProductDownloadController = new OrderProductDownloadController(orderProductDownloadService);
        this.restOrderProductDownloadMockMvc = MockMvcBuilders.standaloneSetup(orderProductDownloadController)
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
    public static OrderProductDownload createEntity(EntityManager em) {
        OrderProductDownload orderProductDownload = new OrderProductDownload()
            .maxdays(DEFAULT_MAXDAYS)
            .downloadCount(DEFAULT_DOWNLOAD_COUNT)
            .orderProductFilename(DEFAULT_ORDER_PRODUCT_FILENAME);
        return orderProductDownload;
    }

    @Before
    public void initTest() {
        orderProductDownloadSearchRepository.deleteAll();
        orderProductDownload = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderProductDownload() throws Exception {
        int databaseSizeBeforeCreate = orderProductDownloadRepository.findAll().size();

        // Create the OrderProductDownload
        restOrderProductDownloadMockMvc.perform(post("/api/order-product-downloads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDownload)))
            .andExpect(status().isCreated());

        // Validate the OrderProductDownload in the database
        List<OrderProductDownload> orderProductDownloadList = orderProductDownloadRepository.findAll();
        assertThat(orderProductDownloadList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProductDownload testOrderProductDownload = orderProductDownloadList.get(orderProductDownloadList.size() - 1);
        assertThat(testOrderProductDownload.getMaxdays()).isEqualTo(DEFAULT_MAXDAYS);
        assertThat(testOrderProductDownload.getDownloadCount()).isEqualTo(DEFAULT_DOWNLOAD_COUNT);
        assertThat(testOrderProductDownload.getOrderProductFilename()).isEqualTo(DEFAULT_ORDER_PRODUCT_FILENAME);

        // Validate the OrderProductDownload in Elasticsearch
        OrderProductDownload orderProductDownloadEs = orderProductDownloadSearchRepository.findOne(testOrderProductDownload.getId());
        assertThat(orderProductDownloadEs).isEqualToComparingFieldByField(testOrderProductDownload);
    }

    @Test
    @Transactional
    public void createOrderProductDownloadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderProductDownloadRepository.findAll().size();

        // Create the OrderProductDownload with an existing ID
        orderProductDownload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductDownloadMockMvc.perform(post("/api/order-product-downloads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDownload)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OrderProductDownload> orderProductDownloadList = orderProductDownloadRepository.findAll();
        assertThat(orderProductDownloadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderProductDownloads() throws Exception {
        // Initialize the database
        orderProductDownloadRepository.saveAndFlush(orderProductDownload);

        // Get all the orderProductDownloadList
        restOrderProductDownloadMockMvc.perform(get("/api/order-product-downloads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductDownload.getId().intValue())))
            .andExpect(jsonPath("$.[*].maxdays").value(hasItem(DEFAULT_MAXDAYS)))
            .andExpect(jsonPath("$.[*].downloadCount").value(hasItem(DEFAULT_DOWNLOAD_COUNT)))
            .andExpect(jsonPath("$.[*].orderProductFilename").value(hasItem(DEFAULT_ORDER_PRODUCT_FILENAME.toString())));
    }

    @Test
    @Transactional
    public void getOrderProductDownload() throws Exception {
        // Initialize the database
        orderProductDownloadRepository.saveAndFlush(orderProductDownload);

        // Get the orderProductDownload
        restOrderProductDownloadMockMvc.perform(get("/api/order-product-downloads/{id}", orderProductDownload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderProductDownload.getId().intValue()))
            .andExpect(jsonPath("$.maxdays").value(DEFAULT_MAXDAYS))
            .andExpect(jsonPath("$.downloadCount").value(DEFAULT_DOWNLOAD_COUNT))
            .andExpect(jsonPath("$.orderProductFilename").value(DEFAULT_ORDER_PRODUCT_FILENAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderProductDownload() throws Exception {
        // Get the orderProductDownload
        restOrderProductDownloadMockMvc.perform(get("/api/order-product-downloads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderProductDownload() throws Exception {
        // Initialize the database
        orderProductDownloadService.save(orderProductDownload);

        int databaseSizeBeforeUpdate = orderProductDownloadRepository.findAll().size();

        // Update the orderProductDownload
        OrderProductDownload updatedOrderProductDownload = orderProductDownloadRepository.findOne(orderProductDownload.getId());
        updatedOrderProductDownload
            .maxdays(UPDATED_MAXDAYS)
            .downloadCount(UPDATED_DOWNLOAD_COUNT)
            .orderProductFilename(UPDATED_ORDER_PRODUCT_FILENAME);

        restOrderProductDownloadMockMvc.perform(put("/api/order-product-downloads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderProductDownload)))
            .andExpect(status().isOk());

        // Validate the OrderProductDownload in the database
        List<OrderProductDownload> orderProductDownloadList = orderProductDownloadRepository.findAll();
        assertThat(orderProductDownloadList).hasSize(databaseSizeBeforeUpdate);
        OrderProductDownload testOrderProductDownload = orderProductDownloadList.get(orderProductDownloadList.size() - 1);
        assertThat(testOrderProductDownload.getMaxdays()).isEqualTo(UPDATED_MAXDAYS);
        assertThat(testOrderProductDownload.getDownloadCount()).isEqualTo(UPDATED_DOWNLOAD_COUNT);
        assertThat(testOrderProductDownload.getOrderProductFilename()).isEqualTo(UPDATED_ORDER_PRODUCT_FILENAME);

        // Validate the OrderProductDownload in Elasticsearch
        OrderProductDownload orderProductDownloadEs = orderProductDownloadSearchRepository.findOne(testOrderProductDownload.getId());
        assertThat(orderProductDownloadEs).isEqualToComparingFieldByField(testOrderProductDownload);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderProductDownload() throws Exception {
        int databaseSizeBeforeUpdate = orderProductDownloadRepository.findAll().size();

        // Create the OrderProductDownload

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderProductDownloadMockMvc.perform(put("/api/order-product-downloads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDownload)))
            .andExpect(status().isCreated());

        // Validate the OrderProductDownload in the database
        List<OrderProductDownload> orderProductDownloadList = orderProductDownloadRepository.findAll();
        assertThat(orderProductDownloadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderProductDownload() throws Exception {
        // Initialize the database
        orderProductDownloadService.save(orderProductDownload);

        int databaseSizeBeforeDelete = orderProductDownloadRepository.findAll().size();

        // Get the orderProductDownload
        restOrderProductDownloadMockMvc.perform(delete("/api/order-product-downloads/{id}", orderProductDownload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean orderProductDownloadExistsInEs = orderProductDownloadSearchRepository.exists(orderProductDownload.getId());
        assertThat(orderProductDownloadExistsInEs).isFalse();

        // Validate the database is empty
        List<OrderProductDownload> orderProductDownloadList = orderProductDownloadRepository.findAll();
        assertThat(orderProductDownloadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOrderProductDownload() throws Exception {
        // Initialize the database
        orderProductDownloadService.save(orderProductDownload);

        // Search the orderProductDownload
        restOrderProductDownloadMockMvc.perform(get("/api/_search/order-product-downloads?query=id:" + orderProductDownload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProductDownload.getId().intValue())))
            .andExpect(jsonPath("$.[*].maxdays").value(hasItem(DEFAULT_MAXDAYS)))
            .andExpect(jsonPath("$.[*].downloadCount").value(hasItem(DEFAULT_DOWNLOAD_COUNT)))
            .andExpect(jsonPath("$.[*].orderProductFilename").value(hasItem(DEFAULT_ORDER_PRODUCT_FILENAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProductDownload.class);
    }
}
