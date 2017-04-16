package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductOptionValueDescription;
import com.smartshop.eshop.repository.ProductOptionValueDescriptionRepository;
import com.smartshop.eshop.service.ProductOptionValueDescriptionService;
import com.smartshop.eshop.repository.search.ProductOptionValueDescriptionSearchRepository;
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
 * Test class for the ProductOptionValueDescriptionResource REST controller.
 *
 * @see ProductOptionValueDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductOptionValueDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductOptionValueDescriptionRepository productOptionValueDescriptionRepository;

    @Autowired
    private ProductOptionValueDescriptionService productOptionValueDescriptionService;

    @Autowired
    private ProductOptionValueDescriptionSearchRepository productOptionValueDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductOptionValueDescriptionMockMvc;

    private ProductOptionValueDescription productOptionValueDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductOptionValueDescriptionController productOptionValueDescriptionController = new ProductOptionValueDescriptionController(productOptionValueDescriptionService);
        this.restProductOptionValueDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productOptionValueDescriptionController)
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
    public static ProductOptionValueDescription createEntity(EntityManager em) {
        ProductOptionValueDescription productOptionValueDescription = new ProductOptionValueDescription()
            .title(DEFAULT_TITLE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productOptionValueDescription;
    }

    @Before
    public void initTest() {
        productOptionValueDescriptionSearchRepository.deleteAll();
        productOptionValueDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOptionValueDescription() throws Exception {
        int databaseSizeBeforeCreate = productOptionValueDescriptionRepository.findAll().size();

        // Create the ProductOptionValueDescription
        restProductOptionValueDescriptionMockMvc.perform(post("/api/product-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValueDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionValueDescription in the database
        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionValueDescription testProductOptionValueDescription = productOptionValueDescriptionList.get(productOptionValueDescriptionList.size() - 1);
        assertThat(testProductOptionValueDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductOptionValueDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionValueDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ProductOptionValueDescription in Elasticsearch
        ProductOptionValueDescription productOptionValueDescriptionEs = productOptionValueDescriptionSearchRepository.findOne(testProductOptionValueDescription.getId());
        assertThat(productOptionValueDescriptionEs).isEqualToComparingFieldByField(testProductOptionValueDescription);
    }

    @Test
    @Transactional
    public void createProductOptionValueDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionValueDescriptionRepository.findAll().size();

        // Create the ProductOptionValueDescription with an existing ID
        productOptionValueDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionValueDescriptionMockMvc.perform(post("/api/product-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValueDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionValueDescriptionRepository.findAll().size();
        // set the field null
        productOptionValueDescription.setName(null);

        // Create the ProductOptionValueDescription, which fails.

        restProductOptionValueDescriptionMockMvc.perform(post("/api/product-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValueDescription)))
            .andExpect(status().isBadRequest());

        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptionValueDescriptions() throws Exception {
        // Initialize the database
        productOptionValueDescriptionRepository.saveAndFlush(productOptionValueDescription);

        // Get all the productOptionValueDescriptionList
        restProductOptionValueDescriptionMockMvc.perform(get("/api/product-option-value-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionValueDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductOptionValueDescription() throws Exception {
        // Initialize the database
        productOptionValueDescriptionRepository.saveAndFlush(productOptionValueDescription);

        // Get the productOptionValueDescription
        restProductOptionValueDescriptionMockMvc.perform(get("/api/product-option-value-descriptions/{id}", productOptionValueDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionValueDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductOptionValueDescription() throws Exception {
        // Get the productOptionValueDescription
        restProductOptionValueDescriptionMockMvc.perform(get("/api/product-option-value-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOptionValueDescription() throws Exception {
        // Initialize the database
        productOptionValueDescriptionService.save(productOptionValueDescription);

        int databaseSizeBeforeUpdate = productOptionValueDescriptionRepository.findAll().size();

        // Update the productOptionValueDescription
        ProductOptionValueDescription updatedProductOptionValueDescription = productOptionValueDescriptionRepository.findOne(productOptionValueDescription.getId());
        updatedProductOptionValueDescription
            .title(UPDATED_TITLE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductOptionValueDescriptionMockMvc.perform(put("/api/product-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOptionValueDescription)))
            .andExpect(status().isOk());

        // Validate the ProductOptionValueDescription in the database
        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionValueDescription testProductOptionValueDescription = productOptionValueDescriptionList.get(productOptionValueDescriptionList.size() - 1);
        assertThat(testProductOptionValueDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductOptionValueDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionValueDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ProductOptionValueDescription in Elasticsearch
        ProductOptionValueDescription productOptionValueDescriptionEs = productOptionValueDescriptionSearchRepository.findOne(testProductOptionValueDescription.getId());
        assertThat(productOptionValueDescriptionEs).isEqualToComparingFieldByField(testProductOptionValueDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOptionValueDescription() throws Exception {
        int databaseSizeBeforeUpdate = productOptionValueDescriptionRepository.findAll().size();

        // Create the ProductOptionValueDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductOptionValueDescriptionMockMvc.perform(put("/api/product-option-value-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionValueDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionValueDescription in the database
        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductOptionValueDescription() throws Exception {
        // Initialize the database
        productOptionValueDescriptionService.save(productOptionValueDescription);

        int databaseSizeBeforeDelete = productOptionValueDescriptionRepository.findAll().size();

        // Get the productOptionValueDescription
        restProductOptionValueDescriptionMockMvc.perform(delete("/api/product-option-value-descriptions/{id}", productOptionValueDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productOptionValueDescriptionExistsInEs = productOptionValueDescriptionSearchRepository.exists(productOptionValueDescription.getId());
        assertThat(productOptionValueDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductOptionValueDescription> productOptionValueDescriptionList = productOptionValueDescriptionRepository.findAll();
        assertThat(productOptionValueDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductOptionValueDescription() throws Exception {
        // Initialize the database
        productOptionValueDescriptionService.save(productOptionValueDescription);

        // Search the productOptionValueDescription
        restProductOptionValueDescriptionMockMvc.perform(get("/api/_search/product-option-value-descriptions?query=id:" + productOptionValueDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionValueDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionValueDescription.class);
    }
}
