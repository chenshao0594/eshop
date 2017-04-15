package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductImageDescription;
import com.smartshop.eshop.repository.ProductImageDescriptionRepository;
import com.smartshop.eshop.service.ProductImageDescriptionService;
import com.smartshop.eshop.repository.search.ProductImageDescriptionSearchRepository;
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
 * Test class for the ProductImageDescriptionResource REST controller.
 *
 * @see ProductImageDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductImageDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_ALT_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductImageDescriptionRepository productImageDescriptionRepository;

    @Autowired
    private ProductImageDescriptionService productImageDescriptionService;

    @Autowired
    private ProductImageDescriptionSearchRepository productImageDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductImageDescriptionMockMvc;

    private ProductImageDescription productImageDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductImageDescriptionController productImageDescriptionController = new ProductImageDescriptionController(productImageDescriptionService);
        this.restProductImageDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productImageDescriptionController)
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
    public static ProductImageDescription createEntity(EntityManager em) {
        ProductImageDescription productImageDescription = new ProductImageDescription()
            .title(DEFAULT_TITLE)
            .altTag(DEFAULT_ALT_TAG)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productImageDescription;
    }

    @Before
    public void initTest() {
        productImageDescriptionSearchRepository.deleteAll();
        productImageDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductImageDescription() throws Exception {
        int databaseSizeBeforeCreate = productImageDescriptionRepository.findAll().size();

        // Create the ProductImageDescription
        restProductImageDescriptionMockMvc.perform(post("/api/product-image-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductImageDescription in the database
        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductImageDescription testProductImageDescription = productImageDescriptionList.get(productImageDescriptionList.size() - 1);
        assertThat(testProductImageDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductImageDescription.getAltTag()).isEqualTo(DEFAULT_ALT_TAG);
        assertThat(testProductImageDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductImageDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ProductImageDescription in Elasticsearch
        ProductImageDescription productImageDescriptionEs = productImageDescriptionSearchRepository.findOne(testProductImageDescription.getId());
        assertThat(productImageDescriptionEs).isEqualToComparingFieldByField(testProductImageDescription);
    }

    @Test
    @Transactional
    public void createProductImageDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productImageDescriptionRepository.findAll().size();

        // Create the ProductImageDescription with an existing ID
        productImageDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductImageDescriptionMockMvc.perform(post("/api/product-image-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productImageDescriptionRepository.findAll().size();
        // set the field null
        productImageDescription.setName(null);

        // Create the ProductImageDescription, which fails.

        restProductImageDescriptionMockMvc.perform(post("/api/product-image-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDescription)))
            .andExpect(status().isBadRequest());

        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductImageDescriptions() throws Exception {
        // Initialize the database
        productImageDescriptionRepository.saveAndFlush(productImageDescription);

        // Get all the productImageDescriptionList
        restProductImageDescriptionMockMvc.perform(get("/api/product-image-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImageDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].altTag").value(hasItem(DEFAULT_ALT_TAG.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductImageDescription() throws Exception {
        // Initialize the database
        productImageDescriptionRepository.saveAndFlush(productImageDescription);

        // Get the productImageDescription
        restProductImageDescriptionMockMvc.perform(get("/api/product-image-descriptions/{id}", productImageDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productImageDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.altTag").value(DEFAULT_ALT_TAG.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductImageDescription() throws Exception {
        // Get the productImageDescription
        restProductImageDescriptionMockMvc.perform(get("/api/product-image-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductImageDescription() throws Exception {
        // Initialize the database
        productImageDescriptionService.save(productImageDescription);

        int databaseSizeBeforeUpdate = productImageDescriptionRepository.findAll().size();

        // Update the productImageDescription
        ProductImageDescription updatedProductImageDescription = productImageDescriptionRepository.findOne(productImageDescription.getId());
        updatedProductImageDescription
            .title(UPDATED_TITLE)
            .altTag(UPDATED_ALT_TAG)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductImageDescriptionMockMvc.perform(put("/api/product-image-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductImageDescription)))
            .andExpect(status().isOk());

        // Validate the ProductImageDescription in the database
        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductImageDescription testProductImageDescription = productImageDescriptionList.get(productImageDescriptionList.size() - 1);
        assertThat(testProductImageDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductImageDescription.getAltTag()).isEqualTo(UPDATED_ALT_TAG);
        assertThat(testProductImageDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductImageDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ProductImageDescription in Elasticsearch
        ProductImageDescription productImageDescriptionEs = productImageDescriptionSearchRepository.findOne(testProductImageDescription.getId());
        assertThat(productImageDescriptionEs).isEqualToComparingFieldByField(testProductImageDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductImageDescription() throws Exception {
        int databaseSizeBeforeUpdate = productImageDescriptionRepository.findAll().size();

        // Create the ProductImageDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductImageDescriptionMockMvc.perform(put("/api/product-image-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImageDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductImageDescription in the database
        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductImageDescription() throws Exception {
        // Initialize the database
        productImageDescriptionService.save(productImageDescription);

        int databaseSizeBeforeDelete = productImageDescriptionRepository.findAll().size();

        // Get the productImageDescription
        restProductImageDescriptionMockMvc.perform(delete("/api/product-image-descriptions/{id}", productImageDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productImageDescriptionExistsInEs = productImageDescriptionSearchRepository.exists(productImageDescription.getId());
        assertThat(productImageDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductImageDescription> productImageDescriptionList = productImageDescriptionRepository.findAll();
        assertThat(productImageDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductImageDescription() throws Exception {
        // Initialize the database
        productImageDescriptionService.save(productImageDescription);

        // Search the productImageDescription
        restProductImageDescriptionMockMvc.perform(get("/api/_search/product-image-descriptions?query=id:" + productImageDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImageDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].altTag").value(hasItem(DEFAULT_ALT_TAG.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImageDescription.class);
    }
}
