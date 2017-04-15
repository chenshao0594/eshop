package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.repository.CategoryRepository;
import com.smartshop.eshop.service.CategoryService;
import com.smartshop.eshop.repository.search.CategorySearchRepository;
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
 * Test class for the CategoryResource REST controller.
 *
 * @see CategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CategoryControllerIntTest {

    private static final Integer DEFAULT_DEPTH = 1;
    private static final Integer UPDATED_DEPTH = 2;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final Boolean DEFAULT_CATEGORY_STATUS = false;
    private static final Boolean UPDATED_CATEGORY_STATUS = true;

    private static final String DEFAULT_LINEAGE = "AAAAAAAAAA";
    private static final String UPDATED_LINEAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_IMAGE = "BBBBBBBBBB";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategorySearchRepository categorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoryMockMvc;

    private Category category;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryController categoryController = new CategoryController(categoryService);
        this.restCategoryMockMvc = MockMvcBuilders.standaloneSetup(categoryController)
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
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .depth(DEFAULT_DEPTH)
            .sortOrder(DEFAULT_SORT_ORDER)
            .categoryStatus(DEFAULT_CATEGORY_STATUS)
            .lineage(DEFAULT_LINEAGE)
            .visible(DEFAULT_VISIBLE)
            .code(DEFAULT_CODE)
            .categoryImage(DEFAULT_CATEGORY_IMAGE);
        return category;
    }

    @Before
    public void initTest() {
        categorySearchRepository.deleteAll();
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getDepth()).isEqualTo(DEFAULT_DEPTH);
        assertThat(testCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testCategory.isCategoryStatus()).isEqualTo(DEFAULT_CATEGORY_STATUS);
        assertThat(testCategory.getLineage()).isEqualTo(DEFAULT_LINEAGE);
        assertThat(testCategory.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testCategory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCategory.getCategoryImage()).isEqualTo(DEFAULT_CATEGORY_IMAGE);

        // Validate the Category in Elasticsearch
        Category categoryEs = categorySearchRepository.findOne(testCategory.getId());
        assertThat(categoryEs).isEqualToComparingFieldByField(testCategory);
    }

    @Test
    @Transactional
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryRepository.findAll().size();
        // set the field null
        category.setCode(null);

        // Create the Category, which fails.

        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].categoryStatus").value(hasItem(DEFAULT_CATEGORY_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].lineage").value(hasItem(DEFAULT_LINEAGE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].categoryImage").value(hasItem(DEFAULT_CATEGORY_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.depth").value(DEFAULT_DEPTH))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.categoryStatus").value(DEFAULT_CATEGORY_STATUS.booleanValue()))
            .andExpect(jsonPath("$.lineage").value(DEFAULT_LINEAGE.toString()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.categoryImage").value(DEFAULT_CATEGORY_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findOne(category.getId());
        updatedCategory
            .depth(UPDATED_DEPTH)
            .sortOrder(UPDATED_SORT_ORDER)
            .categoryStatus(UPDATED_CATEGORY_STATUS)
            .lineage(UPDATED_LINEAGE)
            .visible(UPDATED_VISIBLE)
            .code(UPDATED_CODE)
            .categoryImage(UPDATED_CATEGORY_IMAGE);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategory)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getDepth()).isEqualTo(UPDATED_DEPTH);
        assertThat(testCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testCategory.isCategoryStatus()).isEqualTo(UPDATED_CATEGORY_STATUS);
        assertThat(testCategory.getLineage()).isEqualTo(UPDATED_LINEAGE);
        assertThat(testCategory.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testCategory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCategory.getCategoryImage()).isEqualTo(UPDATED_CATEGORY_IMAGE);

        // Validate the Category in Elasticsearch
        Category categoryEs = categorySearchRepository.findOne(testCategory.getId());
        assertThat(categoryEs).isEqualToComparingFieldByField(testCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Create the Category

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Get the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean categoryExistsInEs = categorySearchRepository.exists(category.getId());
        assertThat(categoryExistsInEs).isFalse();

        // Validate the database is empty
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        // Search the category
        restCategoryMockMvc.perform(get("/api/_search/categories?query=id:" + category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].categoryStatus").value(hasItem(DEFAULT_CATEGORY_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].lineage").value(hasItem(DEFAULT_LINEAGE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].categoryImage").value(hasItem(DEFAULT_CATEGORY_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
    }
}
