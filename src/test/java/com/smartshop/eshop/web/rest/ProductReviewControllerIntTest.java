package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.repository.ProductReviewRepository;
import com.smartshop.eshop.service.ProductReviewService;
import com.smartshop.eshop.repository.search.ProductReviewSearchRepository;
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
 * Test class for the ProductReviewResource REST controller.
 *
 * @see ProductReviewResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductReviewControllerIntTest {

    private static final Long DEFAULT_REVIEW_READ = 1L;
    private static final Long UPDATED_REVIEW_READ = 2L;

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Double DEFAULT_REVIEW_RATING = 1D;
    private static final Double UPDATED_REVIEW_RATING = 2D;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductReviewService productReviewService;

    @Autowired
    private ProductReviewSearchRepository productReviewSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductReviewMockMvc;

    private ProductReview productReview;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductReviewController productReviewController = new ProductReviewController(productReviewService);
        this.restProductReviewMockMvc = MockMvcBuilders.standaloneSetup(productReviewController)
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
    public static ProductReview createEntity(EntityManager em) {
        ProductReview productReview = new ProductReview()
            .reviewRead(DEFAULT_REVIEW_READ)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .status(DEFAULT_STATUS)
            .reviewRating(DEFAULT_REVIEW_RATING);
        return productReview;
    }

    @Before
    public void initTest() {
        productReviewSearchRepository.deleteAll();
        productReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductReview() throws Exception {
        int databaseSizeBeforeCreate = productReviewRepository.findAll().size();

        // Create the ProductReview
        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReview)))
            .andExpect(status().isCreated());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeCreate + 1);
        ProductReview testProductReview = productReviewList.get(productReviewList.size() - 1);
        assertThat(testProductReview.getReviewRead()).isEqualTo(DEFAULT_REVIEW_READ);
        assertThat(testProductReview.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testProductReview.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductReview.getReviewRating()).isEqualTo(DEFAULT_REVIEW_RATING);

        // Validate the ProductReview in Elasticsearch
        ProductReview productReviewEs = productReviewSearchRepository.findOne(testProductReview.getId());
        assertThat(productReviewEs).isEqualToComparingFieldByField(testProductReview);
    }

    @Test
    @Transactional
    public void createProductReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productReviewRepository.findAll().size();

        // Create the ProductReview with an existing ID
        productReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductReviewMockMvc.perform(post("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReview)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductReviews() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        // Get all the productReviewList
        restProductReviewMockMvc.perform(get("/api/product-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewRead").value(hasItem(DEFAULT_REVIEW_READ.intValue())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].reviewRating").value(hasItem(DEFAULT_REVIEW_RATING.doubleValue())));
    }

    @Test
    @Transactional
    public void getProductReview() throws Exception {
        // Initialize the database
        productReviewRepository.saveAndFlush(productReview);

        // Get the productReview
        restProductReviewMockMvc.perform(get("/api/product-reviews/{id}", productReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productReview.getId().intValue()))
            .andExpect(jsonPath("$.reviewRead").value(DEFAULT_REVIEW_READ.intValue()))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.reviewRating").value(DEFAULT_REVIEW_RATING.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductReview() throws Exception {
        // Get the productReview
        restProductReviewMockMvc.perform(get("/api/product-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductReview() throws Exception {
        // Initialize the database
        productReviewService.save(productReview);

        int databaseSizeBeforeUpdate = productReviewRepository.findAll().size();

        // Update the productReview
        ProductReview updatedProductReview = productReviewRepository.findOne(productReview.getId());
        updatedProductReview
            .reviewRead(UPDATED_REVIEW_READ)
            .reviewDate(UPDATED_REVIEW_DATE)
            .status(UPDATED_STATUS)
            .reviewRating(UPDATED_REVIEW_RATING);

        restProductReviewMockMvc.perform(put("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductReview)))
            .andExpect(status().isOk());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeUpdate);
        ProductReview testProductReview = productReviewList.get(productReviewList.size() - 1);
        assertThat(testProductReview.getReviewRead()).isEqualTo(UPDATED_REVIEW_READ);
        assertThat(testProductReview.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testProductReview.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductReview.getReviewRating()).isEqualTo(UPDATED_REVIEW_RATING);

        // Validate the ProductReview in Elasticsearch
        ProductReview productReviewEs = productReviewSearchRepository.findOne(testProductReview.getId());
        assertThat(productReviewEs).isEqualToComparingFieldByField(testProductReview);
    }

    @Test
    @Transactional
    public void updateNonExistingProductReview() throws Exception {
        int databaseSizeBeforeUpdate = productReviewRepository.findAll().size();

        // Create the ProductReview

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductReviewMockMvc.perform(put("/api/product-reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReview)))
            .andExpect(status().isCreated());

        // Validate the ProductReview in the database
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductReview() throws Exception {
        // Initialize the database
        productReviewService.save(productReview);

        int databaseSizeBeforeDelete = productReviewRepository.findAll().size();

        // Get the productReview
        restProductReviewMockMvc.perform(delete("/api/product-reviews/{id}", productReview.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productReviewExistsInEs = productReviewSearchRepository.exists(productReview.getId());
        assertThat(productReviewExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductReview> productReviewList = productReviewRepository.findAll();
        assertThat(productReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductReview() throws Exception {
        // Initialize the database
        productReviewService.save(productReview);

        // Search the productReview
        restProductReviewMockMvc.perform(get("/api/_search/product-reviews?query=id:" + productReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewRead").value(hasItem(DEFAULT_REVIEW_READ.intValue())))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].reviewRating").value(hasItem(DEFAULT_REVIEW_RATING.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReview.class);
    }
}
