package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.Template;
import com.smartshop.eshop.repository.TemplateRepository;
import com.smartshop.eshop.service.TemplateService;
import com.smartshop.eshop.repository.search.TemplateSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class TemplateControllerIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SUPER_ID = 1L;
    private static final Long UPDATED_SUPER_ID = 2L;

    private static final String DEFAULT_TEMPLATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateSearchRepository templateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TemplateController templateController = new TemplateController(templateService);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateController)
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
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .name(DEFAULT_NAME)
            .superId(DEFAULT_SUPER_ID)
            .templateKey(DEFAULT_TEMPLATE_KEY)
            .content(DEFAULT_CONTENT);
        return template;
    }

    @Before
    public void initTest() {
        templateSearchRepository.deleteAll();
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplate.getSuperId()).isEqualTo(DEFAULT_SUPER_ID);
        assertThat(testTemplate.getTemplateKey()).isEqualTo(DEFAULT_TEMPLATE_KEY);
        assertThat(testTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);

        // Validate the Template in Elasticsearch
        Template templateEs = templateSearchRepository.findOne(testTemplate.getId());
        assertThat(templateEs).isEqualToComparingFieldByField(testTemplate);
    }

    @Test
    @Transactional
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        template.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setName(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setTemplateKey(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateRepository.findAll().size();
        // set the field null
        template.setContent(null);

        // Create the Template, which fails.

        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].superId").value(hasItem(DEFAULT_SUPER_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateKey").value(hasItem(DEFAULT_TEMPLATE_KEY.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.superId").value(DEFAULT_SUPER_ID.intValue()))
            .andExpect(jsonPath("$.templateKey").value(DEFAULT_TEMPLATE_KEY.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findOne(template.getId());
        updatedTemplate
            .name(UPDATED_NAME)
            .superId(UPDATED_SUPER_ID)
            .templateKey(UPDATED_TEMPLATE_KEY)
            .content(UPDATED_CONTENT);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplate)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplate.getSuperId()).isEqualTo(UPDATED_SUPER_ID);
        assertThat(testTemplate.getTemplateKey()).isEqualTo(UPDATED_TEMPLATE_KEY);
        assertThat(testTemplate.getContent()).isEqualTo(UPDATED_CONTENT);

        // Validate the Template in Elasticsearch
        Template templateEs = templateSearchRepository.findOne(testTemplate.getId());
        assertThat(templateEs).isEqualToComparingFieldByField(testTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Get the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean templateExistsInEs = templateSearchRepository.exists(template.getId());
        assertThat(templateExistsInEs).isFalse();

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        // Search the template
        restTemplateMockMvc.perform(get("/api/_search/templates?query=id:" + template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].superId").value(hasItem(DEFAULT_SUPER_ID.intValue())))
            .andExpect(jsonPath("$.[*].templateKey").value(hasItem(DEFAULT_TEMPLATE_KEY.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Template.class);
    }
}
