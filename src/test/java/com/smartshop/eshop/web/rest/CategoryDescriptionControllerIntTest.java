package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.CategoryDescription;
import com.smartshop.eshop.repository.CategoryDescriptionRepository;
import com.smartshop.eshop.service.CategoryDescriptionService;
import com.smartshop.eshop.repository.search.CategoryDescriptionSearchRepository;
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
 * Test class for the CategoryDescriptionResource REST controller.
 *
 * @see CategoryDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class CategoryDescriptionControllerIntTest {

    private static final String DEFAULT_CATEGORY_HIGHLIGHT = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_HIGHLIGHT = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_TITLE = "BBBBBBBBBB";

    @Autowired
    private CategoryDescriptionRepository categoryDescriptionRepository;

    @Autowired
    private CategoryDescriptionService categoryDescriptionService;

    @Autowired
    private CategoryDescriptionSearchRepository categoryDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoryDescriptionMockMvc;

    private CategoryDescription categoryDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoryDescriptionController categoryDescriptionController = new CategoryDescriptionController(categoryDescriptionService);
        this.restCategoryDescriptionMockMvc = MockMvcBuilders.standaloneSetup(categoryDescriptionController)
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
    public static CategoryDescription createEntity(EntityManager em) {
        CategoryDescription categoryDescription = new CategoryDescription()
            .categoryHighlight(DEFAULT_CATEGORY_HIGHLIGHT)
            .metatagDescription(DEFAULT_METATAG_DESCRIPTION)
            .seUrl(DEFAULT_SE_URL)
            .metatagKeywords(DEFAULT_METATAG_KEYWORDS)
            .metatagTitle(DEFAULT_METATAG_TITLE);
        return categoryDescription;
    }

    @Before
    public void initTest() {
        categoryDescriptionSearchRepository.deleteAll();
        categoryDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryDescription() throws Exception {
        int databaseSizeBeforeCreate = categoryDescriptionRepository.findAll().size();

        // Create the CategoryDescription
        restCategoryDescriptionMockMvc.perform(post("/api/category-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDescription)))
            .andExpect(status().isCreated());

        // Validate the CategoryDescription in the database
        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryDescription testCategoryDescription = categoryDescriptionList.get(categoryDescriptionList.size() - 1);
        assertThat(testCategoryDescription.getCategoryHighlight()).isEqualTo(DEFAULT_CATEGORY_HIGHLIGHT);
        assertThat(testCategoryDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCategoryDescription.getMetatagDescription()).isEqualTo(DEFAULT_METATAG_DESCRIPTION);
        assertThat(testCategoryDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCategoryDescription.getSeUrl()).isEqualTo(DEFAULT_SE_URL);
        assertThat(testCategoryDescription.getMetatagKeywords()).isEqualTo(DEFAULT_METATAG_KEYWORDS);
        assertThat(testCategoryDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategoryDescription.getMetatagTitle()).isEqualTo(DEFAULT_METATAG_TITLE);

        // Validate the CategoryDescription in Elasticsearch
        CategoryDescription categoryDescriptionEs = categoryDescriptionSearchRepository.findOne(testCategoryDescription.getId());
        assertThat(categoryDescriptionEs).isEqualToComparingFieldByField(testCategoryDescription);
    }

    @Test
    @Transactional
    public void createCategoryDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryDescriptionRepository.findAll().size();

        // Create the CategoryDescription with an existing ID
        categoryDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryDescriptionMockMvc.perform(post("/api/category-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryDescriptionRepository.findAll().size();
        // set the field null
        categoryDescription.setName(null);

        // Create the CategoryDescription, which fails.

        restCategoryDescriptionMockMvc.perform(post("/api/category-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDescription)))
            .andExpect(status().isBadRequest());

        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryDescriptions() throws Exception {
        // Initialize the database
        categoryDescriptionRepository.saveAndFlush(categoryDescription);

        // Get all the categoryDescriptionList
        restCategoryDescriptionMockMvc.perform(get("/api/category-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryHighlight").value(hasItem(DEFAULT_CATEGORY_HIGHLIGHT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getCategoryDescription() throws Exception {
        // Initialize the database
        categoryDescriptionRepository.saveAndFlush(categoryDescription);

        // Get the categoryDescription
        restCategoryDescriptionMockMvc.perform(get("/api/category-descriptions/{id}", categoryDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoryDescription.getId().intValue()))
            .andExpect(jsonPath("$.categoryHighlight").value(DEFAULT_CATEGORY_HIGHLIGHT.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.metatagDescription").value(DEFAULT_METATAG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.seUrl").value(DEFAULT_SE_URL.toString()))
            .andExpect(jsonPath("$.metatagKeywords").value(DEFAULT_METATAG_KEYWORDS.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.metatagTitle").value(DEFAULT_METATAG_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryDescription() throws Exception {
        // Get the categoryDescription
        restCategoryDescriptionMockMvc.perform(get("/api/category-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryDescription() throws Exception {
        // Initialize the database
        categoryDescriptionService.save(categoryDescription);

        int databaseSizeBeforeUpdate = categoryDescriptionRepository.findAll().size();

        // Update the categoryDescription
        CategoryDescription updatedCategoryDescription = categoryDescriptionRepository.findOne(categoryDescription.getId());
        updatedCategoryDescription
            .categoryHighlight(UPDATED_CATEGORY_HIGHLIGHT)
            .metatagDescription(UPDATED_METATAG_DESCRIPTION)
            .seUrl(UPDATED_SE_URL)
            .metatagKeywords(UPDATED_METATAG_KEYWORDS)
            .metatagTitle(UPDATED_METATAG_TITLE);

        restCategoryDescriptionMockMvc.perform(put("/api/category-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoryDescription)))
            .andExpect(status().isOk());

        // Validate the CategoryDescription in the database
        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeUpdate);
        CategoryDescription testCategoryDescription = categoryDescriptionList.get(categoryDescriptionList.size() - 1);
        assertThat(testCategoryDescription.getCategoryHighlight()).isEqualTo(UPDATED_CATEGORY_HIGHLIGHT);
        assertThat(testCategoryDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCategoryDescription.getMetatagDescription()).isEqualTo(UPDATED_METATAG_DESCRIPTION);
        assertThat(testCategoryDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCategoryDescription.getSeUrl()).isEqualTo(UPDATED_SE_URL);
        assertThat(testCategoryDescription.getMetatagKeywords()).isEqualTo(UPDATED_METATAG_KEYWORDS);
        assertThat(testCategoryDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategoryDescription.getMetatagTitle()).isEqualTo(UPDATED_METATAG_TITLE);

        // Validate the CategoryDescription in Elasticsearch
        CategoryDescription categoryDescriptionEs = categoryDescriptionSearchRepository.findOne(testCategoryDescription.getId());
        assertThat(categoryDescriptionEs).isEqualToComparingFieldByField(testCategoryDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryDescription() throws Exception {
        int databaseSizeBeforeUpdate = categoryDescriptionRepository.findAll().size();

        // Create the CategoryDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoryDescriptionMockMvc.perform(put("/api/category-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryDescription)))
            .andExpect(status().isCreated());

        // Validate the CategoryDescription in the database
        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategoryDescription() throws Exception {
        // Initialize the database
        categoryDescriptionService.save(categoryDescription);

        int databaseSizeBeforeDelete = categoryDescriptionRepository.findAll().size();

        // Get the categoryDescription
        restCategoryDescriptionMockMvc.perform(delete("/api/category-descriptions/{id}", categoryDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean categoryDescriptionExistsInEs = categoryDescriptionSearchRepository.exists(categoryDescription.getId());
        assertThat(categoryDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<CategoryDescription> categoryDescriptionList = categoryDescriptionRepository.findAll();
        assertThat(categoryDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCategoryDescription() throws Exception {
        // Initialize the database
        categoryDescriptionService.save(categoryDescription);

        // Search the categoryDescription
        restCategoryDescriptionMockMvc.perform(get("/api/_search/category-descriptions?query=id:" + categoryDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryHighlight").value(hasItem(DEFAULT_CATEGORY_HIGHLIGHT.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryDescription.class);
    }
}
