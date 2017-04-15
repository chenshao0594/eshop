package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductPriceDescription;
import com.smartshop.eshop.repository.ProductPriceDescriptionRepository;
import com.smartshop.eshop.service.ProductPriceDescriptionService;
import com.smartshop.eshop.repository.search.ProductPriceDescriptionSearchRepository;
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
 * Test class for the ProductPriceDescriptionResource REST controller.
 *
 * @see ProductPriceDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductPriceDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_D_EFAULTPRICEDESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_D_EFAULTPRICEDESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductPriceDescriptionRepository productPriceDescriptionRepository;

    @Autowired
    private ProductPriceDescriptionService productPriceDescriptionService;

    @Autowired
    private ProductPriceDescriptionSearchRepository productPriceDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductPriceDescriptionMockMvc;

    private ProductPriceDescription productPriceDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductPriceDescriptionController productPriceDescriptionController = new ProductPriceDescriptionController(productPriceDescriptionService);
        this.restProductPriceDescriptionMockMvc = MockMvcBuilders.standaloneSetup(productPriceDescriptionController)
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
    public static ProductPriceDescription createEntity(EntityManager em) {
        ProductPriceDescription productPriceDescription = new ProductPriceDescription()
            .title(DEFAULT_TITLE)
            .dEFAULTPRICEDESCRIPTION(DEFAULT_D_EFAULTPRICEDESCRIPTION)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productPriceDescription;
    }

    @Before
    public void initTest() {
        productPriceDescriptionSearchRepository.deleteAll();
        productPriceDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPriceDescription() throws Exception {
        int databaseSizeBeforeCreate = productPriceDescriptionRepository.findAll().size();

        // Create the ProductPriceDescription
        restProductPriceDescriptionMockMvc.perform(post("/api/product-price-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductPriceDescription in the database
        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPriceDescription testProductPriceDescription = productPriceDescriptionList.get(productPriceDescriptionList.size() - 1);
        assertThat(testProductPriceDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductPriceDescription.getdEFAULTPRICEDESCRIPTION()).isEqualTo(DEFAULT_D_EFAULTPRICEDESCRIPTION);
        assertThat(testProductPriceDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductPriceDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the ProductPriceDescription in Elasticsearch
        ProductPriceDescription productPriceDescriptionEs = productPriceDescriptionSearchRepository.findOne(testProductPriceDescription.getId());
        assertThat(productPriceDescriptionEs).isEqualToComparingFieldByField(testProductPriceDescription);
    }

    @Test
    @Transactional
    public void createProductPriceDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPriceDescriptionRepository.findAll().size();

        // Create the ProductPriceDescription with an existing ID
        productPriceDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceDescriptionMockMvc.perform(post("/api/product-price-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productPriceDescriptionRepository.findAll().size();
        // set the field null
        productPriceDescription.setName(null);

        // Create the ProductPriceDescription, which fails.

        restProductPriceDescriptionMockMvc.perform(post("/api/product-price-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDescription)))
            .andExpect(status().isBadRequest());

        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductPriceDescriptions() throws Exception {
        // Initialize the database
        productPriceDescriptionRepository.saveAndFlush(productPriceDescription);

        // Get all the productPriceDescriptionList
        restProductPriceDescriptionMockMvc.perform(get("/api/product-price-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPriceDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].dEFAULTPRICEDESCRIPTION").value(hasItem(DEFAULT_D_EFAULTPRICEDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProductPriceDescription() throws Exception {
        // Initialize the database
        productPriceDescriptionRepository.saveAndFlush(productPriceDescription);

        // Get the productPriceDescription
        restProductPriceDescriptionMockMvc.perform(get("/api/product-price-descriptions/{id}", productPriceDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productPriceDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.dEFAULTPRICEDESCRIPTION").value(DEFAULT_D_EFAULTPRICEDESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductPriceDescription() throws Exception {
        // Get the productPriceDescription
        restProductPriceDescriptionMockMvc.perform(get("/api/product-price-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPriceDescription() throws Exception {
        // Initialize the database
        productPriceDescriptionService.save(productPriceDescription);

        int databaseSizeBeforeUpdate = productPriceDescriptionRepository.findAll().size();

        // Update the productPriceDescription
        ProductPriceDescription updatedProductPriceDescription = productPriceDescriptionRepository.findOne(productPriceDescription.getId());
        updatedProductPriceDescription
            .title(UPDATED_TITLE)
            .dEFAULTPRICEDESCRIPTION(UPDATED_D_EFAULTPRICEDESCRIPTION)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductPriceDescriptionMockMvc.perform(put("/api/product-price-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductPriceDescription)))
            .andExpect(status().isOk());

        // Validate the ProductPriceDescription in the database
        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ProductPriceDescription testProductPriceDescription = productPriceDescriptionList.get(productPriceDescriptionList.size() - 1);
        assertThat(testProductPriceDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductPriceDescription.getdEFAULTPRICEDESCRIPTION()).isEqualTo(UPDATED_D_EFAULTPRICEDESCRIPTION);
        assertThat(testProductPriceDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductPriceDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ProductPriceDescription in Elasticsearch
        ProductPriceDescription productPriceDescriptionEs = productPriceDescriptionSearchRepository.findOne(testProductPriceDescription.getId());
        assertThat(productPriceDescriptionEs).isEqualToComparingFieldByField(testProductPriceDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPriceDescription() throws Exception {
        int databaseSizeBeforeUpdate = productPriceDescriptionRepository.findAll().size();

        // Create the ProductPriceDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductPriceDescriptionMockMvc.perform(put("/api/product-price-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPriceDescription)))
            .andExpect(status().isCreated());

        // Validate the ProductPriceDescription in the database
        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductPriceDescription() throws Exception {
        // Initialize the database
        productPriceDescriptionService.save(productPriceDescription);

        int databaseSizeBeforeDelete = productPriceDescriptionRepository.findAll().size();

        // Get the productPriceDescription
        restProductPriceDescriptionMockMvc.perform(delete("/api/product-price-descriptions/{id}", productPriceDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productPriceDescriptionExistsInEs = productPriceDescriptionSearchRepository.exists(productPriceDescription.getId());
        assertThat(productPriceDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductPriceDescription> productPriceDescriptionList = productPriceDescriptionRepository.findAll();
        assertThat(productPriceDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductPriceDescription() throws Exception {
        // Initialize the database
        productPriceDescriptionService.save(productPriceDescription);

        // Search the productPriceDescription
        restProductPriceDescriptionMockMvc.perform(get("/api/_search/product-price-descriptions?query=id:" + productPriceDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPriceDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].dEFAULTPRICEDESCRIPTION").value(hasItem(DEFAULT_D_EFAULTPRICEDESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPriceDescription.class);
    }
}
