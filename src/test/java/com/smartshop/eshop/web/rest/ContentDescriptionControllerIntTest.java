package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.repository.ContentDescriptionRepository;
import com.smartshop.eshop.service.ContentDescriptionService;
import com.smartshop.eshop.repository.search.ContentDescriptionSearchRepository;
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
 * Test class for the ContentDescriptionResource REST controller.
 *
 * @see ContentDescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ContentDescriptionControllerIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_METATAG_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_METATAG_KEYWORDS = "BBBBBBBBBB";

    @Autowired
    private ContentDescriptionRepository contentDescriptionRepository;

    @Autowired
    private ContentDescriptionService contentDescriptionService;

    @Autowired
    private ContentDescriptionSearchRepository contentDescriptionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentDescriptionMockMvc;

    private ContentDescription contentDescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentDescriptionController contentDescriptionController = new ContentDescriptionController(contentDescriptionService);
        this.restContentDescriptionMockMvc = MockMvcBuilders.standaloneSetup(contentDescriptionController)
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
    public static ContentDescription createEntity(EntityManager em) {
        ContentDescription contentDescription = new ContentDescription()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .metatagTitle(DEFAULT_METATAG_TITLE)
            .metatagDescription(DEFAULT_METATAG_DESCRIPTION)
            .name(DEFAULT_NAME)
            .seUrl(DEFAULT_SE_URL)
            .metatagKeywords(DEFAULT_METATAG_KEYWORDS);
        return contentDescription;
    }

    @Before
    public void initTest() {
        contentDescriptionSearchRepository.deleteAll();
        contentDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentDescription() throws Exception {
        int databaseSizeBeforeCreate = contentDescriptionRepository.findAll().size();

        // Create the ContentDescription
        restContentDescriptionMockMvc.perform(post("/api/content-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDescription)))
            .andExpect(status().isCreated());

        // Validate the ContentDescription in the database
        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ContentDescription testContentDescription = contentDescriptionList.get(contentDescriptionList.size() - 1);
        assertThat(testContentDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testContentDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testContentDescription.getMetatagTitle()).isEqualTo(DEFAULT_METATAG_TITLE);
        assertThat(testContentDescription.getMetatagDescription()).isEqualTo(DEFAULT_METATAG_DESCRIPTION);
        assertThat(testContentDescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContentDescription.getSeUrl()).isEqualTo(DEFAULT_SE_URL);
        assertThat(testContentDescription.getMetatagKeywords()).isEqualTo(DEFAULT_METATAG_KEYWORDS);

        // Validate the ContentDescription in Elasticsearch
        ContentDescription contentDescriptionEs = contentDescriptionSearchRepository.findOne(testContentDescription.getId());
        assertThat(contentDescriptionEs).isEqualToComparingFieldByField(testContentDescription);
    }

    @Test
    @Transactional
    public void createContentDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentDescriptionRepository.findAll().size();

        // Create the ContentDescription with an existing ID
        contentDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentDescriptionMockMvc.perform(post("/api/content-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDescription)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentDescriptionRepository.findAll().size();
        // set the field null
        contentDescription.setName(null);

        // Create the ContentDescription, which fails.

        restContentDescriptionMockMvc.perform(post("/api/content-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDescription)))
            .andExpect(status().isBadRequest());

        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentDescriptions() throws Exception {
        // Initialize the database
        contentDescriptionRepository.saveAndFlush(contentDescription);

        // Get all the contentDescriptionList
        restContentDescriptionMockMvc.perform(get("/api/content-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())));
    }

    @Test
    @Transactional
    public void getContentDescription() throws Exception {
        // Initialize the database
        contentDescriptionRepository.saveAndFlush(contentDescription);

        // Get the contentDescription
        restContentDescriptionMockMvc.perform(get("/api/content-descriptions/{id}", contentDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.metatagTitle").value(DEFAULT_METATAG_TITLE.toString()))
            .andExpect(jsonPath("$.metatagDescription").value(DEFAULT_METATAG_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.seUrl").value(DEFAULT_SE_URL.toString()))
            .andExpect(jsonPath("$.metatagKeywords").value(DEFAULT_METATAG_KEYWORDS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContentDescription() throws Exception {
        // Get the contentDescription
        restContentDescriptionMockMvc.perform(get("/api/content-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentDescription() throws Exception {
        // Initialize the database
        contentDescriptionService.save(contentDescription);

        int databaseSizeBeforeUpdate = contentDescriptionRepository.findAll().size();

        // Update the contentDescription
        ContentDescription updatedContentDescription = contentDescriptionRepository.findOne(contentDescription.getId());
        updatedContentDescription
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .metatagTitle(UPDATED_METATAG_TITLE)
            .metatagDescription(UPDATED_METATAG_DESCRIPTION)
            .name(UPDATED_NAME)
            .seUrl(UPDATED_SE_URL)
            .metatagKeywords(UPDATED_METATAG_KEYWORDS);

        restContentDescriptionMockMvc.perform(put("/api/content-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentDescription)))
            .andExpect(status().isOk());

        // Validate the ContentDescription in the database
        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ContentDescription testContentDescription = contentDescriptionList.get(contentDescriptionList.size() - 1);
        assertThat(testContentDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testContentDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testContentDescription.getMetatagTitle()).isEqualTo(UPDATED_METATAG_TITLE);
        assertThat(testContentDescription.getMetatagDescription()).isEqualTo(UPDATED_METATAG_DESCRIPTION);
        assertThat(testContentDescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContentDescription.getSeUrl()).isEqualTo(UPDATED_SE_URL);
        assertThat(testContentDescription.getMetatagKeywords()).isEqualTo(UPDATED_METATAG_KEYWORDS);

        // Validate the ContentDescription in Elasticsearch
        ContentDescription contentDescriptionEs = contentDescriptionSearchRepository.findOne(testContentDescription.getId());
        assertThat(contentDescriptionEs).isEqualToComparingFieldByField(testContentDescription);
    }

    @Test
    @Transactional
    public void updateNonExistingContentDescription() throws Exception {
        int databaseSizeBeforeUpdate = contentDescriptionRepository.findAll().size();

        // Create the ContentDescription

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentDescriptionMockMvc.perform(put("/api/content-descriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentDescription)))
            .andExpect(status().isCreated());

        // Validate the ContentDescription in the database
        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentDescription() throws Exception {
        // Initialize the database
        contentDescriptionService.save(contentDescription);

        int databaseSizeBeforeDelete = contentDescriptionRepository.findAll().size();

        // Get the contentDescription
        restContentDescriptionMockMvc.perform(delete("/api/content-descriptions/{id}", contentDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean contentDescriptionExistsInEs = contentDescriptionSearchRepository.exists(contentDescription.getId());
        assertThat(contentDescriptionExistsInEs).isFalse();

        // Validate the database is empty
        List<ContentDescription> contentDescriptionList = contentDescriptionRepository.findAll();
        assertThat(contentDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchContentDescription() throws Exception {
        // Initialize the database
        contentDescriptionService.save(contentDescription);

        // Search the contentDescription
        restContentDescriptionMockMvc.perform(get("/api/_search/content-descriptions?query=id:" + contentDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].metatagTitle").value(hasItem(DEFAULT_METATAG_TITLE.toString())))
            .andExpect(jsonPath("$.[*].metatagDescription").value(hasItem(DEFAULT_METATAG_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].seUrl").value(hasItem(DEFAULT_SE_URL.toString())))
            .andExpect(jsonPath("$.[*].metatagKeywords").value(hasItem(DEFAULT_METATAG_KEYWORDS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDescription.class);
    }
}
