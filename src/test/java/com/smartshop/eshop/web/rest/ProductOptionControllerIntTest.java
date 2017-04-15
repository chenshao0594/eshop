package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.repository.ProductOptionRepository;
import com.smartshop.eshop.service.ProductOptionService;
import com.smartshop.eshop.repository.search.ProductOptionSearchRepository;
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
 * Test class for the ProductOptionResource REST controller.
 *
 * @see ProductOptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductOptionControllerIntTest {

    private static final Boolean DEFAULT_READ_ONLY = false;
    private static final Boolean UPDATED_READ_ONLY = true;

    private static final String DEFAULT_PRODUCT_OPTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_OPTION_SORT_ORDER = 1;
    private static final Integer UPDATED_PRODUCT_OPTION_SORT_ORDER = 2;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductOptionSearchRepository productOptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductOptionMockMvc;

    private ProductOption productOption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductOptionController productOptionController = new ProductOptionController(productOptionService);
        this.restProductOptionMockMvc = MockMvcBuilders.standaloneSetup(productOptionController)
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
    public static ProductOption createEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .readOnly(DEFAULT_READ_ONLY)
            .productOptionType(DEFAULT_PRODUCT_OPTION_TYPE)
            .code(DEFAULT_CODE)
            .productOptionSortOrder(DEFAULT_PRODUCT_OPTION_SORT_ORDER);
        return productOption;
    }

    @Before
    public void initTest() {
        productOptionSearchRepository.deleteAll();
        productOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOption() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOption)))
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.isReadOnly()).isEqualTo(DEFAULT_READ_ONLY);
        assertThat(testProductOption.getProductOptionType()).isEqualTo(DEFAULT_PRODUCT_OPTION_TYPE);
        assertThat(testProductOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductOption.getProductOptionSortOrder()).isEqualTo(DEFAULT_PRODUCT_OPTION_SORT_ORDER);

        // Validate the ProductOption in Elasticsearch
        ProductOption productOptionEs = productOptionSearchRepository.findOne(testProductOption.getId());
        assertThat(productOptionEs).isEqualToComparingFieldByField(testProductOption);
    }

    @Test
    @Transactional
    public void createProductOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption with an existing ID
        productOption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOption)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionRepository.findAll().size();
        // set the field null
        productOption.setCode(null);

        // Create the ProductOption, which fails.

        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOption)))
            .andExpect(status().isBadRequest());

        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptions() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList
        restProductOptionMockMvc.perform(get("/api/product-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].readOnly").value(hasItem(DEFAULT_READ_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionType").value(hasItem(DEFAULT_PRODUCT_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productOptionSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void getProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOption.getId().intValue()))
            .andExpect(jsonPath("$.readOnly").value(DEFAULT_READ_ONLY.booleanValue()))
            .andExpect(jsonPath("$.productOptionType").value(DEFAULT_PRODUCT_OPTION_TYPE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.productOptionSortOrder").value(DEFAULT_PRODUCT_OPTION_SORT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingProductOption() throws Exception {
        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOption() throws Exception {
        // Initialize the database
        productOptionService.save(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption
        ProductOption updatedProductOption = productOptionRepository.findOne(productOption.getId());
        updatedProductOption
            .readOnly(UPDATED_READ_ONLY)
            .productOptionType(UPDATED_PRODUCT_OPTION_TYPE)
            .code(UPDATED_CODE)
            .productOptionSortOrder(UPDATED_PRODUCT_OPTION_SORT_ORDER);

        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOption)))
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.isReadOnly()).isEqualTo(UPDATED_READ_ONLY);
        assertThat(testProductOption.getProductOptionType()).isEqualTo(UPDATED_PRODUCT_OPTION_TYPE);
        assertThat(testProductOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOption.getProductOptionSortOrder()).isEqualTo(UPDATED_PRODUCT_OPTION_SORT_ORDER);

        // Validate the ProductOption in Elasticsearch
        ProductOption productOptionEs = productOptionSearchRepository.findOne(testProductOption.getId());
        assertThat(productOptionEs).isEqualToComparingFieldByField(testProductOption);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Create the ProductOption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOption)))
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductOption() throws Exception {
        // Initialize the database
        productOptionService.save(productOption);

        int databaseSizeBeforeDelete = productOptionRepository.findAll().size();

        // Get the productOption
        restProductOptionMockMvc.perform(delete("/api/product-options/{id}", productOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productOptionExistsInEs = productOptionSearchRepository.exists(productOption.getId());
        assertThat(productOptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductOption() throws Exception {
        // Initialize the database
        productOptionService.save(productOption);

        // Search the productOption
        restProductOptionMockMvc.perform(get("/api/_search/product-options?query=id:" + productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].readOnly").value(hasItem(DEFAULT_READ_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].productOptionType").value(hasItem(DEFAULT_PRODUCT_OPTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productOptionSortOrder").value(hasItem(DEFAULT_PRODUCT_OPTION_SORT_ORDER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOption.class);
    }
}
