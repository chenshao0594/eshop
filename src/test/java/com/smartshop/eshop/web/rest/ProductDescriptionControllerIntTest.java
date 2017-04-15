package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.repository.ProductDescriptionRepository;
import com.smartshop.eshop.service.ProductDescriptionService;
import com.smartshop.eshop.repository.search.ProductDescriptionSearchRepository;
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
 * Test class for the ProductDescriptionResource REST controller.
 *
 * @see ProductDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductDescriptionControllerIntTest {

    private static final String DEFAULT_METATAG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_HIGHLIGHT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_HIGHLIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_EXTERNAL_DL = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_EXTERNAL_DL = "BBBBBBBBBB";

    @Autowired
    private ProductDescriptionRepository productDescriptionRepository;

    @Autowired
    private ProductDescriptionService productDescriptionService;

    @Autowired
    private ProductDescriptionSearchRepository productDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductDescriptionMockMvc;

    private ProductDescription productDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductDescriptionController productDescriptionController = new ProductDescriptionController(productDescriptionService);
        this.restProductDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productDescriptionController)
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
    public static ProductDescription createEntity(EntityManager em) {
        ProductDescription productDescription = new ProductDescription()
            .metatagDescription(DEFAULT_METATAG_DESCRIPTION)
            .seUrl(DEFAULT_SE_URL)
            .metatagKeywords(DEFAULT_METATAG_KEYWORDS)
            .productHighlight(DEFAULT_PRODUCT_HIGHLIGHT)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .metatagTitle(DEFAULT_METATAG_TITLE)
            .name(DEFAULT_NAME)
            .productExternalDl(DEFAULT_PRODUCT_EXTERNAL_DL);
        return productDescription;
    }

    @Before
    public void initTest() {
        productDescriptionSearchRepository.deleteAll();
        productDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductDescription() throws Exception {
        int databaseSizeBeforeCreate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription
        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDescription testProductDescription = productDescriptionList.get(productDescriptionList.size() - 1);
        assertThat(testProductDescription.getMetatagDescription()).isEqualTo(DEFAULT_METATAG_DESCRIPTION);
        assertThat(testProductDescription.getSeUrl()).isEqualTo(DEFAULT_SE_URL);
        assertThat(testProductDescription.getMetatagKeywords()).isEqualTo(DEFAULT_METATAG_KEYWORDS);
        assertThat(testProductDescription.getProductHighlight()).isEqualTo(DEFAULT_PRODUCT_HIGHLIGHT);
        assertThat(testProductDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductDescription.getMetatagTitle()).isEqualTo(DEFAULT_METATAG_TITLE);
        assertThat(testProductDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductDescription.getProductExternalDl()).isEqualTo(DEFAULT_PRODUCT_EXTERNAL_DL);

        // Validate the ProductDescription in Elasticsearch
        ProductDescription productDescriptionEs = productDescriptionSearchRepository.findOne(testProductDescription.getId());
        assertThat(productDescriptionEs).isEqualToComparingFieldByField(testProductDescription);
    }

    @Test
    @Transactional
    public void createProductDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription with an existing ID
        productDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDescriptionRepository.findAll().size();
        // set the field null
        productDescription.setName(null);

        // Create the ProductDescription, which fails.

        restProductDescriptionMockMvc.perform(post("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescription)))
            .andExpect(status().isBadRequest());

        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductDescriptions() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get all the productDescriptionList
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].productHighlight").value(hasItem(DEFAULT_PRODUCT_HIGHLIGHT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productExternalDl").value(hasItem(DEFAULT_PRODUCT_EXTERNAL_DL.toString())));
    }

    @Test
    @Transactional
    public void getProductDescription() throws Exception {
        // Initialize the database
        productDescriptionRepository.saveAndFlush(productDescription);

        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", productDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productDescription.getId().intValue()))
            .andExpect(jsonPath("$.metatagDescription").value(DEFAULT_METATAG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.seUrl").value(DEFAULT_SE_URL.toString()))
            .andExpect(jsonPath("$.metatagKeywords").value(DEFAULT_METATAG_KEYWORDS.toString()))
            .andExpect(jsonPath("$.productHighlight").value(DEFAULT_PRODUCT_HIGHLIGHT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.metatagTitle").value(DEFAULT_METATAG_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.productExternalDl").value(DEFAULT_PRODUCT_EXTERNAL_DL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductDescription() throws Exception {
        // Get the productDescription
        restProductDescriptionMockMvc.perform(get("/api/product-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductDescription() throws Exception {
        // Initialize the database
        productDescriptionService.save(productDescription);

        int databaseSizeBeforeUpdate = productDescriptionRepository.findAll().size();

        // Update the productDescription
        ProductDescription updatedProductDescription = productDescriptionRepository.findOne(productDescription.getId());
        updatedProductDescription
            .metatagDescription(UPDATED_METATAG_DESCRIPTION)
            .seUrl(UPDATED_SE_URL)
            .metatagKeywords(UPDATED_METATAG_KEYWORDS)
            .productHighlight(UPDATED_PRODUCT_HIGHLIGHT)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .metatagTitle(UPDATED_METATAG_TITLE)
            .name(UPDATED_NAME)
            .productExternalDl(UPDATED_PRODUCT_EXTERNAL_DL);

        restProductDescriptionMockMvc.perform(put("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductDescription)))
            .andExpect(status().isOk());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductDescription testProductDescription = productDescriptionList.get(productDescriptionList.size() - 1);
        assertThat(testProductDescription.getMetatagDescription()).isEqualTo(UPDATED_METATAG_DESCRIPTION);
        assertThat(testProductDescription.getSeUrl()).isEqualTo(UPDATED_SE_URL);
        assertThat(testProductDescription.getMetatagKeywords()).isEqualTo(UPDATED_METATAG_KEYWORDS);
        assertThat(testProductDescription.getProductHighlight()).isEqualTo(UPDATED_PRODUCT_HIGHLIGHT);
        assertThat(testProductDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductDescription.getMetatagTitle()).isEqualTo(UPDATED_METATAG_TITLE);
        assertThat(testProductDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductDescription.getProductExternalDl()).isEqualTo(UPDATED_PRODUCT_EXTERNAL_DL);

        // Validate the ProductDescription in Elasticsearch
        ProductDescription productDescriptionEs = productDescriptionSearchRepository.findOne(testProductDescription.getId());
        assertThat(productDescriptionEs).isEqualToComparingFieldByField(testProductDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductDescription() throws Exception {
        int databaseSizeBeforeUpdate = productDescriptionRepository.findAll().size();

        // Create the ProductDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductDescriptionMockMvc.perform(put("/api/product-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductDescription in the database
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductDescription() throws Exception {
        // Initialize the database
        productDescriptionService.save(productDescription);

        int databaseSizeBeforeDelete = productDescriptionRepository.findAll().size();

        // Get the productDescription
        restProductDescriptionMockMvc.perform(delete("/api/product-descriptions/{id}", productDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productDescriptionExistsInEs = productDescriptionSearchRepository.exists(productDescription.getId());
        assertThat(productDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductDescription> productDescriptionList = productDescriptionRepository.findAll();
        assertThat(productDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductDescription() throws Exception {
        // Initialize the database
        productDescriptionService.save(productDescription);

        // Search the productDescription
        restProductDescriptionMockMvc.perform(get("/api/_search/product-descriptions?query=id:" + productDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].productHighlight").value(hasItem(DEFAULT_PRODUCT_HIGHLIGHT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].productExternalDl").value(hasItem(DEFAULT_PRODUCT_EXTERNAL_DL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductDescription.class);
    }
}
