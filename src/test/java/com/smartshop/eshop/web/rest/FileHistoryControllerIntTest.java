package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.FileHistory;
import com.smartshop.eshop.repository.FileHistoryRepository;
import com.smartshop.eshop.service.FileHistoryService;
import com.smartshop.eshop.repository.search.FileHistorySearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FileHistoryResource REST controller.
 *
 * @see FileHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class FileHistoryControllerIntTest {

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DOWNLOAD_COUNT = 1;
    private static final Integer UPDATED_DOWNLOAD_COUNT = 2;

    private static final Long DEFAULT_FILE_ID = 1L;
    private static final Long UPDATED_FILE_ID = 2L;

    private static final LocalDate DEFAULT_ACCOUNTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCOUNTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_FILESIZE = 1;
    private static final Integer UPDATED_FILESIZE = 2;

    @Autowired
    private FileHistoryRepository fileHistoryRepository;

    @Autowired
    private FileHistoryService fileHistoryService;

    @Autowired
    private FileHistorySearchRepository fileHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFileHistoryMockMvc;

    private FileHistory fileHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FileHistoryController fileHistoryController = new FileHistoryController(fileHistoryService);
        this.restFileHistoryMockMvc = MockMvcBuilders.standaloneSetup(fileHistoryController)
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
    public static FileHistory createEntity(EntityManager em) {
        FileHistory fileHistory = new FileHistory()
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .downloadCount(DEFAULT_DOWNLOAD_COUNT)
            .fileId(DEFAULT_FILE_ID)
            .accountedDate(DEFAULT_ACCOUNTED_DATE)
            .filesize(DEFAULT_FILESIZE);
        return fileHistory;
    }

    @Before
    public void initTest() {
        fileHistorySearchRepository.deleteAll();
        fileHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileHistory() throws Exception {
        int databaseSizeBeforeCreate = fileHistoryRepository.findAll().size();

        // Create the FileHistory
        restFileHistoryMockMvc.perform(post("/api/file-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileHistory)))
            .andExpect(status().isCreated());

        // Validate the FileHistory in the database
        List<FileHistory> fileHistoryList = fileHistoryRepository.findAll();
        assertThat(fileHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        FileHistory testFileHistory = fileHistoryList.get(fileHistoryList.size() - 1);
        assertThat(testFileHistory.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testFileHistory.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testFileHistory.getDownloadCount()).isEqualTo(DEFAULT_DOWNLOAD_COUNT);
        assertThat(testFileHistory.getFileId()).isEqualTo(DEFAULT_FILE_ID);
        assertThat(testFileHistory.getAccountedDate()).isEqualTo(DEFAULT_ACCOUNTED_DATE);
        assertThat(testFileHistory.getFilesize()).isEqualTo(DEFAULT_FILESIZE);

        // Validate the FileHistory in Elasticsearch
        FileHistory fileHistoryEs = fileHistorySearchRepository.findOne(testFileHistory.getId());
        assertThat(fileHistoryEs).isEqualToComparingFieldByField(testFileHistory);
    }

    @Test
    @Transactional
    public void createFileHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileHistoryRepository.findAll().size();

        // Create the FileHistory with an existing ID
        fileHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileHistoryMockMvc.perform(post("/api/file-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FileHistory> fileHistoryList = fileHistoryRepository.findAll();
        assertThat(fileHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFileHistories() throws Exception {
        // Initialize the database
        fileHistoryRepository.saveAndFlush(fileHistory);

        // Get all the fileHistoryList
        restFileHistoryMockMvc.perform(get("/api/file-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].downloadCount").value(hasItem(DEFAULT_DOWNLOAD_COUNT)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountedDate").value(hasItem(DEFAULT_ACCOUNTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE)));
    }

    @Test
    @Transactional
    public void getFileHistory() throws Exception {
        // Initialize the database
        fileHistoryRepository.saveAndFlush(fileHistory);

        // Get the fileHistory
        restFileHistoryMockMvc.perform(get("/api/file-histories/{id}", fileHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileHistory.getId().intValue()))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.downloadCount").value(DEFAULT_DOWNLOAD_COUNT))
            .andExpect(jsonPath("$.fileId").value(DEFAULT_FILE_ID.intValue()))
            .andExpect(jsonPath("$.accountedDate").value(DEFAULT_ACCOUNTED_DATE.toString()))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE));
    }

    @Test
    @Transactional
    public void getNonExistingFileHistory() throws Exception {
        // Get the fileHistory
        restFileHistoryMockMvc.perform(get("/api/file-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileHistory() throws Exception {
        // Initialize the database
        fileHistoryService.save(fileHistory);

        int databaseSizeBeforeUpdate = fileHistoryRepository.findAll().size();

        // Update the fileHistory
        FileHistory updatedFileHistory = fileHistoryRepository.findOne(fileHistory.getId());
        updatedFileHistory
            .dateAdded(UPDATED_DATE_ADDED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .downloadCount(UPDATED_DOWNLOAD_COUNT)
            .fileId(UPDATED_FILE_ID)
            .accountedDate(UPDATED_ACCOUNTED_DATE)
            .filesize(UPDATED_FILESIZE);

        restFileHistoryMockMvc.perform(put("/api/file-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileHistory)))
            .andExpect(status().isOk());

        // Validate the FileHistory in the database
        List<FileHistory> fileHistoryList = fileHistoryRepository.findAll();
        assertThat(fileHistoryList).hasSize(databaseSizeBeforeUpdate);
        FileHistory testFileHistory = fileHistoryList.get(fileHistoryList.size() - 1);
        assertThat(testFileHistory.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testFileHistory.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testFileHistory.getDownloadCount()).isEqualTo(UPDATED_DOWNLOAD_COUNT);
        assertThat(testFileHistory.getFileId()).isEqualTo(UPDATED_FILE_ID);
        assertThat(testFileHistory.getAccountedDate()).isEqualTo(UPDATED_ACCOUNTED_DATE);
        assertThat(testFileHistory.getFilesize()).isEqualTo(UPDATED_FILESIZE);

        // Validate the FileHistory in Elasticsearch
        FileHistory fileHistoryEs = fileHistorySearchRepository.findOne(testFileHistory.getId());
        assertThat(fileHistoryEs).isEqualToComparingFieldByField(testFileHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingFileHistory() throws Exception {
        int databaseSizeBeforeUpdate = fileHistoryRepository.findAll().size();

        // Create the FileHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileHistoryMockMvc.perform(put("/api/file-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileHistory)))
            .andExpect(status().isCreated());

        // Validate the FileHistory in the database
        List<FileHistory> fileHistoryList = fileHistoryRepository.findAll();
        assertThat(fileHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFileHistory() throws Exception {
        // Initialize the database
        fileHistoryService.save(fileHistory);

        int databaseSizeBeforeDelete = fileHistoryRepository.findAll().size();

        // Get the fileHistory
        restFileHistoryMockMvc.perform(delete("/api/file-histories/{id}", fileHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean fileHistoryExistsInEs = fileHistorySearchRepository.exists(fileHistory.getId());
        assertThat(fileHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<FileHistory> fileHistoryList = fileHistoryRepository.findAll();
        assertThat(fileHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFileHistory() throws Exception {
        // Initialize the database
        fileHistoryService.save(fileHistory);

        // Search the fileHistory
        restFileHistoryMockMvc.perform(get("/api/_search/file-histories?query=id:" + fileHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].downloadCount").value(hasItem(DEFAULT_DOWNLOAD_COUNT)))
            .andExpect(jsonPath("$.[*].fileId").value(hasItem(DEFAULT_FILE_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountedDate").value(hasItem(DEFAULT_ACCOUNTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileHistory.class);
    }
}
