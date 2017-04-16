package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductOptionDescription;
import com.smartshop.eshop.repository.ProductOptionDescriptionRepository;
import com.smartshop.eshop.service.ProductOptionDescriptionService;
import com.smartshop.eshop.repository.search.ProductOptionDescriptionSearchRepository;
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
 * Test class for the ProductOptionDescriptionResource REST controller.
 *
 * @see ProductOptionDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductOptionDescriptionControllerIntTest {

    private static final String DEFAULT_PRODUCT_OPTION_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductOptionDescriptionRepository productOptionDescriptionRepository;

    @Autowired
    private ProductOptionDescriptionService productOptionDescriptionService;

    @Autowired
    private ProductOptionDescriptionSearchRepository productOptionDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductOptionDescriptionMockMvc;

    private ProductOptionDescription productOptionDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductOptionDescriptionController productOptionDescriptionController = new ProductOptionDescriptionController(productOptionDescriptionService);
        this.restProductOptionDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productOptionDescriptionController)
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
    public static ProductOptionDescription createEntity(EntityManager em) {
        ProductOptionDescription productOptionDescription = new ProductOptionDescription()
            .productOptionComment(DEFAULT_PRODUCT_OPTION_COMMENT)
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productOptionDescription;
    }

    @Before
    public void initTest() {
        productOptionDescriptionSearchRepository.deleteAll();
        productOptionDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOptionDescription() throws Exception {
        int databaseSizeBeforeCreate = productOptionDescriptionRepository.findAll().size();

        // Create the ProductOptionDescription
        restProductOptionDescriptionMockMvc.perform(post("/api/product-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionDescription in the database
        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionDescription testProductOptionDescription = productOptionDescriptionList.get(productOptionDescriptionList.size() - 1);
        assertThat(testProductOptionDescription.getProductOptionComment()).isEqualTo(DEFAULT_PRODUCT_OPTION_COMMENT);
        assertThat(testProductOptionDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductOptionDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ProductOptionDescription in Elasticsearch
        ProductOptionDescription productOptionDescriptionEs = productOptionDescriptionSearchRepository.findOne(testProductOptionDescription.getId());
        assertThat(productOptionDescriptionEs).isEqualToComparingFieldByField(testProductOptionDescription);
    }

    @Test
    @Transactional
    public void createProductOptionDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionDescriptionRepository.findAll().size();

        // Create the ProductOptionDescription with an existing ID
        productOptionDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionDescriptionMockMvc.perform(post("/api/product-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionDescriptionRepository.findAll().size();
        // set the field null
        productOptionDescription.setName(null);

        // Create the ProductOptionDescription, which fails.

        restProductOptionDescriptionMockMvc.perform(post("/api/product-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDescription)))
            .andExpect(status().isBadRequest());

        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptionDescriptions() throws Exception {
        // Initialize the database
        productOptionDescriptionRepository.saveAndFlush(productOptionDescription);

        // Get all the productOptionDescriptionList
        restProductOptionDescriptionMockMvc.perform(get("/api/product-option-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionComment").value(hasItem(DEFAULT_PRODUCT_OPTION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductOptionDescription() throws Exception {
        // Initialize the database
        productOptionDescriptionRepository.saveAndFlush(productOptionDescription);

        // Get the productOptionDescription
        restProductOptionDescriptionMockMvc.perform(get("/api/product-option-descriptions/{id}", productOptionDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionDescription.getId().intValue()))
            .andExpect(jsonPath("$.productOptionComment").value(DEFAULT_PRODUCT_OPTION_COMMENT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductOptionDescription() throws Exception {
        // Get the productOptionDescription
        restProductOptionDescriptionMockMvc.perform(get("/api/product-option-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOptionDescription() throws Exception {
        // Initialize the database
        productOptionDescriptionService.save(productOptionDescription);

        int databaseSizeBeforeUpdate = productOptionDescriptionRepository.findAll().size();

        // Update the productOptionDescription
        ProductOptionDescription updatedProductOptionDescription = productOptionDescriptionRepository.findOne(productOptionDescription.getId());
        updatedProductOptionDescription
            .productOptionComment(UPDATED_PRODUCT_OPTION_COMMENT)
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductOptionDescriptionMockMvc.perform(put("/api/product-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOptionDescription)))
            .andExpect(status().isOk());

        // Validate the ProductOptionDescription in the database
        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionDescription testProductOptionDescription = productOptionDescriptionList.get(productOptionDescriptionList.size() - 1);
        assertThat(testProductOptionDescription.getProductOptionComment()).isEqualTo(UPDATED_PRODUCT_OPTION_COMMENT);
        assertThat(testProductOptionDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductOptionDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ProductOptionDescription in Elasticsearch
        ProductOptionDescription productOptionDescriptionEs = productOptionDescriptionSearchRepository.findOne(testProductOptionDescription.getId());
        assertThat(productOptionDescriptionEs).isEqualToComparingFieldByField(testProductOptionDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOptionDescription() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDescriptionRepository.findAll().size();

        // Create the ProductOptionDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductOptionDescriptionMockMvc.perform(put("/api/product-option-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionDescription in the database
        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductOptionDescription() throws Exception {
        // Initialize the database
        productOptionDescriptionService.save(productOptionDescription);

        int databaseSizeBeforeDelete = productOptionDescriptionRepository.findAll().size();

        // Get the productOptionDescription
        restProductOptionDescriptionMockMvc.perform(delete("/api/product-option-descriptions/{id}", productOptionDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productOptionDescriptionExistsInEs = productOptionDescriptionSearchRepository.exists(productOptionDescription.getId());
        assertThat(productOptionDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductOptionDescription> productOptionDescriptionList = productOptionDescriptionRepository.findAll();
        assertThat(productOptionDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductOptionDescription() throws Exception {
        // Initialize the database
        productOptionDescriptionService.save(productOptionDescription);

        // Search the productOptionDescription
        restProductOptionDescriptionMockMvc.perform(get("/api/_search/product-option-descriptions?query=id:" + productOptionDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionComment").value(hasItem(DEFAULT_PRODUCT_OPTION_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionDescription.class);
    }
}
