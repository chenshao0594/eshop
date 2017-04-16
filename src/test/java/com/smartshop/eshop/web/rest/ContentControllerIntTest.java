package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Content;
import com.smartshop.eshop.repository.ContentRepository;
import com.smartshop.eshop.service.ContentService;
import com.smartshop.eshop.repository.search.ContentSearchRepository;
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

import com.smartshop.eshop.domain.enumeration.ContentTypeEnum;
import com.smartshop.eshop.domain.enumeration.ContentPositionEnum;
/**
 * Test class for the ContentResource REST controller.
 *
 * @see ContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ContentControllerIntTest {

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;

    private static final String DEFAULT_PRODUCT_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_GROUP = "BBBBBBBBBB";

    private static final ContentTypeEnum DEFAULT_CONTENT_TYPE = ContentTypeEnum.BOX;
    private static final ContentTypeEnum UPDATED_CONTENT_TYPE = ContentTypeEnum.PAGE;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VISIBLE = false;
    private static final Boolean UPDATED_VISIBLE = true;

    private static final ContentPositionEnum DEFAULT_CONTENT_POSITION = ContentPositionEnum.LEFT;
    private static final ContentPositionEnum UPDATED_CONTENT_POSITION = ContentPositionEnum.RIGHT;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentSearchRepository contentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentMockMvc;

    private Content content;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentController contentController = new ContentController(contentService);
        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(contentController)
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
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .sortOrder(DEFAULT_SORT_ORDER)
            .productGroup(DEFAULT_PRODUCT_GROUP)
            .contentType(DEFAULT_CONTENT_TYPE)
            .code(DEFAULT_CODE)
            .visible(DEFAULT_VISIBLE)
            .contentPosition(DEFAULT_CONTENT_POSITION);
        return content;
    }

    @Before
    public void initTest() {
        contentSearchRepository.deleteAll();
        content = createEntity(em);
    }

    @Test
    @Transactional
    public void createContent() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate + 1);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testContent.getProductGroup()).isEqualTo(DEFAULT_PRODUCT_GROUP);
        assertThat(testContent.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testContent.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContent.isVisible()).isEqualTo(DEFAULT_VISIBLE);
        assertThat(testContent.getContentPosition()).isEqualTo(DEFAULT_CONTENT_POSITION);

        // Validate the Content in Elasticsearch
        Content contentEs = contentSearchRepository.findOne(testContent.getId());
        assertThat(contentEs).isEqualToComparingFieldByField(testContent);
    }

    @Test
    @Transactional
    public void createContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentRepository.findAll().size();

        // Create the Content with an existing ID
        content.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentRepository.findAll().size();
        // set the field null
        content.setCode(null);

        // Create the Content, which fails.

        restContentMockMvc.perform(post("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isBadRequest());

        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc.perform(get("/api/contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productGroup").value(hasItem(DEFAULT_PRODUCT_GROUP.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].contentPosition").value(hasItem(DEFAULT_CONTENT_POSITION.toString())));
    }

    @Test
    @Transactional
    public void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.productGroup").value(DEFAULT_PRODUCT_GROUP.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.visible").value(DEFAULT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.contentPosition").value(DEFAULT_CONTENT_POSITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get("/api/contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContent() throws Exception {
        // Initialize the database
        contentService.save(content);

        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Update the content
        Content updatedContent = contentRepository.findOne(content.getId());
        updatedContent
            .sortOrder(UPDATED_SORT_ORDER)
            .productGroup(UPDATED_PRODUCT_GROUP)
            .contentType(UPDATED_CONTENT_TYPE)
            .code(UPDATED_CODE)
            .visible(UPDATED_VISIBLE)
            .contentPosition(UPDATED_CONTENT_POSITION);

        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContent)))
            .andExpect(status().isOk());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate);
        Content testContent = contentList.get(contentList.size() - 1);
        assertThat(testContent.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testContent.getProductGroup()).isEqualTo(UPDATED_PRODUCT_GROUP);
        assertThat(testContent.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testContent.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContent.isVisible()).isEqualTo(UPDATED_VISIBLE);
        assertThat(testContent.getContentPosition()).isEqualTo(UPDATED_CONTENT_POSITION);

        // Validate the Content in Elasticsearch
        Content contentEs = contentSearchRepository.findOne(testContent.getId());
        assertThat(contentEs).isEqualToComparingFieldByField(testContent);
    }

    @Test
    @Transactional
    public void updateNonExistingContent() throws Exception {
        int databaseSizeBeforeUpdate = contentRepository.findAll().size();

        // Create the Content

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentMockMvc.perform(put("/api/contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(content)))
            .andExpect(status().isCreated());

        // Validate the Content in the database
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContent() throws Exception {
        // Initialize the database
        contentService.save(content);

        int databaseSizeBeforeDelete = contentRepository.findAll().size();

        // Get the content
        restContentMockMvc.perform(delete("/api/contents/{id}", content.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean contentExistsInEs = contentSearchRepository.exists(content.getId());
        assertThat(contentExistsInEs).isFalse();

        // Validate the database is empty
        List<Content> contentList = contentRepository.findAll();
        assertThat(contentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchContent() throws Exception {
        // Initialize the database
        contentService.save(content);

        // Search the content
        restContentMockMvc.perform(get("/api/_search/contents?query=id:" + content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].productGroup").value(hasItem(DEFAULT_PRODUCT_GROUP.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].visible").value(hasItem(DEFAULT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].contentPosition").value(hasItem(DEFAULT_CONTENT_POSITION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Content.class);
    }
}
