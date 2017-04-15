package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductReviewDescription;
import com.smartshop.eshop.repository.ProductReviewDescriptionRepository;
import com.smartshop.eshop.service.ProductReviewDescriptionService;
import com.smartshop.eshop.repository.search.ProductReviewDescriptionSearchRepository;
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
 * Test class for the ProductReviewDescriptionResource REST controller.
 *
 * @see ProductReviewDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductReviewDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductReviewDescriptionRepository productReviewDescriptionRepository;

    @Autowired
    private ProductReviewDescriptionService productReviewDescriptionService;

    @Autowired
    private ProductReviewDescriptionSearchRepository productReviewDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductReviewDescriptionMockMvc;

    private ProductReviewDescription productReviewDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductReviewDescriptionController productReviewDescriptionController = new ProductReviewDescriptionController(productReviewDescriptionService);
        this.restProductReviewDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productReviewDescriptionController)
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
    public static ProductReviewDescription createEntity(EntityManager em) {
        ProductReviewDescription productReviewDescription = new ProductReviewDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productReviewDescription;
    }

    @Before
    public void initTest() {
        productReviewDescriptionSearchRepository.deleteAll();
        productReviewDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductReviewDescription() throws Exception {
        int databaseSizeBeforeCreate = productReviewDescriptionRepository.findAll().size();

        // Create the ProductReviewDescription
        restProductReviewDescriptionMockMvc.perform(post("/api/product-review-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductReviewDescription in the database
        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductReviewDescription testProductReviewDescription = productReviewDescriptionList.get(productReviewDescriptionList.size() - 1);
        assertThat(testProductReviewDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductReviewDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductReviewDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ProductReviewDescription in Elasticsearch
        ProductReviewDescription productReviewDescriptionEs = productReviewDescriptionSearchRepository.findOne(testProductReviewDescription.getId());
        assertThat(productReviewDescriptionEs).isEqualToComparingFieldByField(testProductReviewDescription);
    }

    @Test
    @Transactional
    public void createProductReviewDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productReviewDescriptionRepository.findAll().size();

        // Create the ProductReviewDescription with an existing ID
        productReviewDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductReviewDescriptionMockMvc.perform(post("/api/product-review-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productReviewDescriptionRepository.findAll().size();
        // set the field null
        productReviewDescription.setName(null);

        // Create the ProductReviewDescription, which fails.

        restProductReviewDescriptionMockMvc.perform(post("/api/product-review-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDescription)))
            .andExpect(status().isBadRequest());

        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductReviewDescriptions() throws Exception {
        // Initialize the database
        productReviewDescriptionRepository.saveAndFlush(productReviewDescription);

        // Get all the productReviewDescriptionList
        restProductReviewDescriptionMockMvc.perform(get("/api/product-review-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReviewDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductReviewDescription() throws Exception {
        // Initialize the database
        productReviewDescriptionRepository.saveAndFlush(productReviewDescription);

        // Get the productReviewDescription
        restProductReviewDescriptionMockMvc.perform(get("/api/product-review-descriptions/{id}", productReviewDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productReviewDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductReviewDescription() throws Exception {
        // Get the productReviewDescription
        restProductReviewDescriptionMockMvc.perform(get("/api/product-review-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductReviewDescription() throws Exception {
        // Initialize the database
        productReviewDescriptionService.save(productReviewDescription);

        int databaseSizeBeforeUpdate = productReviewDescriptionRepository.findAll().size();

        // Update the productReviewDescription
        ProductReviewDescription updatedProductReviewDescription = productReviewDescriptionRepository.findOne(productReviewDescription.getId());
        updatedProductReviewDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductReviewDescriptionMockMvc.perform(put("/api/product-review-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductReviewDescription)))
            .andExpect(status().isOk());

        // Validate the ProductReviewDescription in the database
        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductReviewDescription testProductReviewDescription = productReviewDescriptionList.get(productReviewDescriptionList.size() - 1);
        assertThat(testProductReviewDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductReviewDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductReviewDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ProductReviewDescription in Elasticsearch
        ProductReviewDescription productReviewDescriptionEs = productReviewDescriptionSearchRepository.findOne(testProductReviewDescription.getId());
        assertThat(productReviewDescriptionEs).isEqualToComparingFieldByField(testProductReviewDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductReviewDescription() throws Exception {
        int databaseSizeBeforeUpdate = productReviewDescriptionRepository.findAll().size();

        // Create the ProductReviewDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductReviewDescriptionMockMvc.perform(put("/api/product-review-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productReviewDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductReviewDescription in the database
        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductReviewDescription() throws Exception {
        // Initialize the database
        productReviewDescriptionService.save(productReviewDescription);

        int databaseSizeBeforeDelete = productReviewDescriptionRepository.findAll().size();

        // Get the productReviewDescription
        restProductReviewDescriptionMockMvc.perform(delete("/api/product-review-descriptions/{id}", productReviewDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productReviewDescriptionExistsInEs = productReviewDescriptionSearchRepository.exists(productReviewDescription.getId());
        assertThat(productReviewDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductReviewDescription> productReviewDescriptionList = productReviewDescriptionRepository.findAll();
        assertThat(productReviewDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductReviewDescription() throws Exception {
        // Initialize the database
        productReviewDescriptionService.save(productReviewDescription);

        // Search the productReviewDescription
        restProductReviewDescriptionMockMvc.perform(get("/api/_search/product-review-descriptions?query=id:" + productReviewDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productReviewDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductReviewDescription.class);
    }
}
