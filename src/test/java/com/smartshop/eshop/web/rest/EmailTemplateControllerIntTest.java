package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.EmailTemplate;
import com.smartshop.eshop.repository.EmailTemplateRepository;
import com.smartshop.eshop.service.EmailTemplateService;
import com.smartshop.eshop.repository.search.EmailTemplateSearchRepository;
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
 * Test class for the EmailTemplateResource REST controller.
 *
 * @see EmailTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class EmailTemplateControllerIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_KEY = "BBBBBBBBBB";

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private EmailTemplateSearchRepository emailTemplateSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailTemplateMockMvc;

    private EmailTemplate emailTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmailTemplateController emailTemplateController = new EmailTemplateController(emailTemplateService);
        this.restEmailTemplateMockMvc = MockMvcBuilders.standaloneSetup(emailTemplateController)
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
    public static EmailTemplate createEntity(EntityManager em) {
        EmailTemplate emailTemplate = new EmailTemplate()
            .name(DEFAULT_NAME)
            .subject(DEFAULT_SUBJECT)
            .content(DEFAULT_CONTENT)
            .actionKey(DEFAULT_ACTION_KEY);
        return emailTemplate;
    }

    @Before
    public void initTest() {
        emailTemplateSearchRepository.deleteAll();
        emailTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTemplate() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate
        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
            .andExpect(status().isCreated());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmailTemplate.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmailTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testEmailTemplate.getActionKey()).isEqualTo(DEFAULT_ACTION_KEY);

        // Validate the EmailTemplate in Elasticsearch
        EmailTemplate emailTemplateEs = emailTemplateSearchRepository.findOne(testEmailTemplate.getId());
        assertThat(emailTemplateEs).isEqualToComparingFieldByField(testEmailTemplate);
    }

    @Test
    @Transactional
    public void createEmailTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate with an existing ID
        emailTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setName(null);

        // Create the EmailTemplate, which fails.

        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTemplateRepository.findAll().size();
        // set the field null
        emailTemplate.setSubject(null);

        // Create the EmailTemplate, which fails.

        restEmailTemplateMockMvc.perform(post("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
            .andExpect(status().isBadRequest());

        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailTemplates() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get all the emailTemplateList
        restEmailTemplateMockMvc.perform(get("/api/email-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].actionKey").value(hasItem(DEFAULT_ACTION_KEY.toString())));
    }

    @Test
    @Transactional
    public void getEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateRepository.saveAndFlush(emailTemplate);

        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.actionKey").value(DEFAULT_ACTION_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmailTemplate() throws Exception {
        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/email-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateService.save(emailTemplate);

        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Update the emailTemplate
        EmailTemplate updatedEmailTemplate = emailTemplateRepository.findOne(emailTemplate.getId());
        updatedEmailTemplate
            .name(UPDATED_NAME)
            .subject(UPDATED_SUBJECT)
            .content(UPDATED_CONTENT)
            .actionKey(UPDATED_ACTION_KEY);

        restEmailTemplateMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmailTemplate)))
            .andExpect(status().isOk());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate);
        EmailTemplate testEmailTemplate = emailTemplateList.get(emailTemplateList.size() - 1);
        assertThat(testEmailTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmailTemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testEmailTemplate.getActionKey()).isEqualTo(UPDATED_ACTION_KEY);

        // Validate the EmailTemplate in Elasticsearch
        EmailTemplate emailTemplateEs = emailTemplateSearchRepository.findOne(testEmailTemplate.getId());
        assertThat(emailTemplateEs).isEqualToComparingFieldByField(testEmailTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTemplate() throws Exception {
        int databaseSizeBeforeUpdate = emailTemplateRepository.findAll().size();

        // Create the EmailTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailTemplateMockMvc.perform(put("/api/email-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailTemplate)))
            .andExpect(status().isCreated());

        // Validate the EmailTemplate in the database
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateService.save(emailTemplate);

        int databaseSizeBeforeDelete = emailTemplateRepository.findAll().size();

        // Get the emailTemplate
        restEmailTemplateMockMvc.perform(delete("/api/email-templates/{id}", emailTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean emailTemplateExistsInEs = emailTemplateSearchRepository.exists(emailTemplate.getId());
        assertThat(emailTemplateExistsInEs).isFalse();

        // Validate the database is empty
        List<EmailTemplate> emailTemplateList = emailTemplateRepository.findAll();
        assertThat(emailTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmailTemplate() throws Exception {
        // Initialize the database
        emailTemplateService.save(emailTemplate);

        // Search the emailTemplate
        restEmailTemplateMockMvc.perform(get("/api/_search/email-templates?query=id:" + emailTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].actionKey").value(hasItem(DEFAULT_ACTION_KEY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplate.class);
    }
}
