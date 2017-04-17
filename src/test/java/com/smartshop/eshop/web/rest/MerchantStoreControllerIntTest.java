package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.repository.MerchantStoreRepository;
import com.smartshop.eshop.service.MerchantStoreService;
import com.smartshop.eshop.repository.search.MerchantStoreSearchRepository;
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
 * Test class for the MerchantStoreResource REST controller.
 *
 * @see MerchantStoreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class MerchantStoreControllerIntTest {

    private static final String DEFAULT_STOREADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STOREADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STORENAME = "AAAAAAAAAA";
    private static final String UPDATED_STORENAME = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STORE_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_D_EFAULTSTORE = "AAAAAAAAAA";
    private static final String UPDATED_D_EFAULTSTORE = "BBBBBBBBBB";

    private static final String DEFAULT_STOREPHONE = "AAAAAAAAAA";
    private static final String UPDATED_STOREPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WEIGHTUNITCODE = "AAAAAAAAAA";
    private static final String UPDATED_WEIGHTUNITCODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CACHE = false;
    private static final Boolean UPDATED_USE_CACHE = true;

    private static final String DEFAULT_STORE_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_STORE_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_STORE_LOGO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_IN_BUSINESS_SINCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_IN_BUSINESS_SINCE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_CURRENCY_FORMAT_NATIONAL = false;
    private static final Boolean UPDATED_CURRENCY_FORMAT_NATIONAL = true;

    private static final String DEFAULT_STOREPOSTALCODE = "AAAAAAAAAA";
    private static final String UPDATED_STOREPOSTALCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SEIZEUNITCODE = "AAAAAAAAAA";
    private static final String UPDATED_SEIZEUNITCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STORESTATEPROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STORESTATEPROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTINUESHOPPINGURL = "AAAAAAAAAA";
    private static final String UPDATED_CONTINUESHOPPINGURL = "BBBBBBBBBB";

    private static final String DEFAULT_STORECITY = "AAAAAAAAAA";
    private static final String UPDATED_STORECITY = "BBBBBBBBBB";

    @Autowired
    private MerchantStoreRepository merchantStoreRepository;

    @Autowired
    private MerchantStoreService merchantStoreService;

    @Autowired
    private MerchantStoreSearchRepository merchantStoreSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMerchantStoreMockMvc;

    private MerchantStore merchantStore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MerchantStoreController merchantStoreController = new MerchantStoreController(merchantStoreService);
        this.restMerchantStoreMockMvc = MockMvcBuilders.standaloneSetup(merchantStoreController)
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
    public static MerchantStore createEntity(EntityManager em) {
        MerchantStore merchantStore = new MerchantStore()
            .storeaddress(DEFAULT_STOREADDRESS)
            .code(DEFAULT_CODE)
            .storename(DEFAULT_STORENAME)
            .storeEmailAddress(DEFAULT_STORE_EMAIL_ADDRESS)
            .storephone(DEFAULT_STOREPHONE)
            .weightunitcode(DEFAULT_WEIGHTUNITCODE)
            .useCache(DEFAULT_USE_CACHE)
            .storeTemplate(DEFAULT_STORE_TEMPLATE)
            .domainName(DEFAULT_DOMAIN_NAME)
            .invoiceTemplate(DEFAULT_INVOICE_TEMPLATE)
            .storeLogo(DEFAULT_STORE_LOGO)
            .currencyFormatNational(DEFAULT_CURRENCY_FORMAT_NATIONAL)
            .storepostalcode(DEFAULT_STOREPOSTALCODE)
            .seizeunitcode(DEFAULT_SEIZEUNITCODE)
            .storestateprovince(DEFAULT_STORESTATEPROVINCE)
            .continueshoppingurl(DEFAULT_CONTINUESHOPPINGURL)
            .storecity(DEFAULT_STORECITY);
        return merchantStore;
    }

    @Before
    public void initTest() {
        merchantStoreSearchRepository.deleteAll();
        merchantStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createMerchantStore() throws Exception {
        int databaseSizeBeforeCreate = merchantStoreRepository.findAll().size();

        // Create the MerchantStore
        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isCreated());

        // Validate the MerchantStore in the database
        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeCreate + 1);
        MerchantStore testMerchantStore = merchantStoreList.get(merchantStoreList.size() - 1);
        assertThat(testMerchantStore.getStoreaddress()).isEqualTo(DEFAULT_STOREADDRESS);
        assertThat(testMerchantStore.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMerchantStore.getStorename()).isEqualTo(DEFAULT_STORENAME);
        assertThat(testMerchantStore.getStoreEmailAddress()).isEqualTo(DEFAULT_STORE_EMAIL_ADDRESS);
        assertThat(testMerchantStore.getStorephone()).isEqualTo(DEFAULT_STOREPHONE);
        assertThat(testMerchantStore.getWeightunitcode()).isEqualTo(DEFAULT_WEIGHTUNITCODE);
        assertThat(testMerchantStore.isUseCache()).isEqualTo(DEFAULT_USE_CACHE);
        assertThat(testMerchantStore.getStoreTemplate()).isEqualTo(DEFAULT_STORE_TEMPLATE);
        assertThat(testMerchantStore.getDomainName()).isEqualTo(DEFAULT_DOMAIN_NAME);
        assertThat(testMerchantStore.getInvoiceTemplate()).isEqualTo(DEFAULT_INVOICE_TEMPLATE);
        assertThat(testMerchantStore.getStoreLogo()).isEqualTo(DEFAULT_STORE_LOGO);
        assertThat(testMerchantStore.isCurrencyFormatNational()).isEqualTo(DEFAULT_CURRENCY_FORMAT_NATIONAL);
        assertThat(testMerchantStore.getStorepostalcode()).isEqualTo(DEFAULT_STOREPOSTALCODE);
        assertThat(testMerchantStore.getSeizeunitcode()).isEqualTo(DEFAULT_SEIZEUNITCODE);
        assertThat(testMerchantStore.getStorestateprovince()).isEqualTo(DEFAULT_STORESTATEPROVINCE);
        assertThat(testMerchantStore.getContinueshoppingurl()).isEqualTo(DEFAULT_CONTINUESHOPPINGURL);
        assertThat(testMerchantStore.getStorecity()).isEqualTo(DEFAULT_STORECITY);

        // Validate the MerchantStore in Elasticsearch
        MerchantStore merchantStoreEs = merchantStoreSearchRepository.findOne(testMerchantStore.getId());
        assertThat(merchantStoreEs).isEqualToComparingFieldByField(testMerchantStore);
    }

    @Test
    @Transactional
    public void createMerchantStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = merchantStoreRepository.findAll().size();

        // Create the MerchantStore with an existing ID
        merchantStore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setCode(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStorenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setStorename(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setStoreEmailAddress(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStorephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setStorephone(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStorepostalcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setStorepostalcode(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStorecityIsRequired() throws Exception {
        int databaseSizeBeforeTest = merchantStoreRepository.findAll().size();
        // set the field null
        merchantStore.setStorecity(null);

        // Create the MerchantStore, which fails.

        restMerchantStoreMockMvc.perform(post("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isBadRequest());

        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMerchantStores() throws Exception {
        // Initialize the database
        merchantStoreRepository.saveAndFlush(merchantStore);

        // Get all the merchantStoreList
        restMerchantStoreMockMvc.perform(get("/api/merchant-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].storeaddress").value(hasItem(DEFAULT_STOREADDRESS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].storename").value(hasItem(DEFAULT_STORENAME.toString())))
            .andExpect(jsonPath("$.[*].storeEmailAddress").value(hasItem(DEFAULT_STORE_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].dEFAULTSTORE").value(hasItem(DEFAULT_D_EFAULTSTORE.toString())))
            .andExpect(jsonPath("$.[*].storephone").value(hasItem(DEFAULT_STOREPHONE.toString())))
            .andExpect(jsonPath("$.[*].weightunitcode").value(hasItem(DEFAULT_WEIGHTUNITCODE.toString())))
            .andExpect(jsonPath("$.[*].useCache").value(hasItem(DEFAULT_USE_CACHE.booleanValue())))
            .andExpect(jsonPath("$.[*].storeTemplate").value(hasItem(DEFAULT_STORE_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].invoiceTemplate").value(hasItem(DEFAULT_INVOICE_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].storeLogo").value(hasItem(DEFAULT_STORE_LOGO.toString())))
            .andExpect(jsonPath("$.[*].inBusinessSince").value(hasItem(DEFAULT_IN_BUSINESS_SINCE.toString())))
            .andExpect(jsonPath("$.[*].currencyFormatNational").value(hasItem(DEFAULT_CURRENCY_FORMAT_NATIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].storepostalcode").value(hasItem(DEFAULT_STOREPOSTALCODE.toString())))
            .andExpect(jsonPath("$.[*].seizeunitcode").value(hasItem(DEFAULT_SEIZEUNITCODE.toString())))
            .andExpect(jsonPath("$.[*].storestateprovince").value(hasItem(DEFAULT_STORESTATEPROVINCE.toString())))
            .andExpect(jsonPath("$.[*].continueshoppingurl").value(hasItem(DEFAULT_CONTINUESHOPPINGURL.toString())))
            .andExpect(jsonPath("$.[*].storecity").value(hasItem(DEFAULT_STORECITY.toString())));
    }

    @Test
    @Transactional
    public void getMerchantStore() throws Exception {
        // Initialize the database
        merchantStoreRepository.saveAndFlush(merchantStore);

        // Get the merchantStore
        restMerchantStoreMockMvc.perform(get("/api/merchant-stores/{id}", merchantStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchantStore.getId().intValue()))
            .andExpect(jsonPath("$.storeaddress").value(DEFAULT_STOREADDRESS.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.storename").value(DEFAULT_STORENAME.toString()))
            .andExpect(jsonPath("$.storeEmailAddress").value(DEFAULT_STORE_EMAIL_ADDRESS.toString()))
            .andExpect(jsonPath("$.dEFAULTSTORE").value(DEFAULT_D_EFAULTSTORE.toString()))
            .andExpect(jsonPath("$.storephone").value(DEFAULT_STOREPHONE.toString()))
            .andExpect(jsonPath("$.weightunitcode").value(DEFAULT_WEIGHTUNITCODE.toString()))
            .andExpect(jsonPath("$.useCache").value(DEFAULT_USE_CACHE.booleanValue()))
            .andExpect(jsonPath("$.storeTemplate").value(DEFAULT_STORE_TEMPLATE.toString()))
            .andExpect(jsonPath("$.domainName").value(DEFAULT_DOMAIN_NAME.toString()))
            .andExpect(jsonPath("$.invoiceTemplate").value(DEFAULT_INVOICE_TEMPLATE.toString()))
            .andExpect(jsonPath("$.storeLogo").value(DEFAULT_STORE_LOGO.toString()))
            .andExpect(jsonPath("$.inBusinessSince").value(DEFAULT_IN_BUSINESS_SINCE.toString()))
            .andExpect(jsonPath("$.currencyFormatNational").value(DEFAULT_CURRENCY_FORMAT_NATIONAL.booleanValue()))
            .andExpect(jsonPath("$.storepostalcode").value(DEFAULT_STOREPOSTALCODE.toString()))
            .andExpect(jsonPath("$.seizeunitcode").value(DEFAULT_SEIZEUNITCODE.toString()))
            .andExpect(jsonPath("$.storestateprovince").value(DEFAULT_STORESTATEPROVINCE.toString()))
            .andExpect(jsonPath("$.continueshoppingurl").value(DEFAULT_CONTINUESHOPPINGURL.toString()))
            .andExpect(jsonPath("$.storecity").value(DEFAULT_STORECITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMerchantStore() throws Exception {
        // Get the merchantStore
        restMerchantStoreMockMvc.perform(get("/api/merchant-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMerchantStore() throws Exception {
        // Initialize the database
        merchantStoreService.save(merchantStore);

        int databaseSizeBeforeUpdate = merchantStoreRepository.findAll().size();

        // Update the merchantStore
        MerchantStore updatedMerchantStore = merchantStoreRepository.findOne(merchantStore.getId());
        updatedMerchantStore
            .storeaddress(UPDATED_STOREADDRESS)
            .code(UPDATED_CODE)
            .storename(UPDATED_STORENAME)
            .storeEmailAddress(UPDATED_STORE_EMAIL_ADDRESS)
            .storephone(UPDATED_STOREPHONE)
            .weightunitcode(UPDATED_WEIGHTUNITCODE)
            .useCache(UPDATED_USE_CACHE)
            .storeTemplate(UPDATED_STORE_TEMPLATE)
            .domainName(UPDATED_DOMAIN_NAME)
            .invoiceTemplate(UPDATED_INVOICE_TEMPLATE)
            .storeLogo(UPDATED_STORE_LOGO)
            .currencyFormatNational(UPDATED_CURRENCY_FORMAT_NATIONAL)
            .storepostalcode(UPDATED_STOREPOSTALCODE)
            .seizeunitcode(UPDATED_SEIZEUNITCODE)
            .storestateprovince(UPDATED_STORESTATEPROVINCE)
            .continueshoppingurl(UPDATED_CONTINUESHOPPINGURL)
            .storecity(UPDATED_STORECITY);

        restMerchantStoreMockMvc.perform(put("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMerchantStore)))
            .andExpect(status().isOk());

        // Validate the MerchantStore in the database
        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeUpdate);
        MerchantStore testMerchantStore = merchantStoreList.get(merchantStoreList.size() - 1);
        assertThat(testMerchantStore.getStoreaddress()).isEqualTo(UPDATED_STOREADDRESS);
        assertThat(testMerchantStore.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMerchantStore.getStorename()).isEqualTo(UPDATED_STORENAME);
        assertThat(testMerchantStore.getStoreEmailAddress()).isEqualTo(UPDATED_STORE_EMAIL_ADDRESS);
        assertThat(testMerchantStore.getStorephone()).isEqualTo(UPDATED_STOREPHONE);
        assertThat(testMerchantStore.getWeightunitcode()).isEqualTo(UPDATED_WEIGHTUNITCODE);
        assertThat(testMerchantStore.isUseCache()).isEqualTo(UPDATED_USE_CACHE);
        assertThat(testMerchantStore.getStoreTemplate()).isEqualTo(UPDATED_STORE_TEMPLATE);
        assertThat(testMerchantStore.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testMerchantStore.getInvoiceTemplate()).isEqualTo(UPDATED_INVOICE_TEMPLATE);
        assertThat(testMerchantStore.getStoreLogo()).isEqualTo(UPDATED_STORE_LOGO);
        assertThat(testMerchantStore.isCurrencyFormatNational()).isEqualTo(UPDATED_CURRENCY_FORMAT_NATIONAL);
        assertThat(testMerchantStore.getStorepostalcode()).isEqualTo(UPDATED_STOREPOSTALCODE);
        assertThat(testMerchantStore.getSeizeunitcode()).isEqualTo(UPDATED_SEIZEUNITCODE);
        assertThat(testMerchantStore.getStorestateprovince()).isEqualTo(UPDATED_STORESTATEPROVINCE);
        assertThat(testMerchantStore.getContinueshoppingurl()).isEqualTo(UPDATED_CONTINUESHOPPINGURL);
        assertThat(testMerchantStore.getStorecity()).isEqualTo(UPDATED_STORECITY);

        // Validate the MerchantStore in Elasticsearch
        MerchantStore merchantStoreEs = merchantStoreSearchRepository.findOne(testMerchantStore.getId());
        assertThat(merchantStoreEs).isEqualToComparingFieldByField(testMerchantStore);
    }

    @Test
    @Transactional
    public void updateNonExistingMerchantStore() throws Exception {
        int databaseSizeBeforeUpdate = merchantStoreRepository.findAll().size();

        // Create the MerchantStore

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMerchantStoreMockMvc.perform(put("/api/merchant-stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(merchantStore)))
            .andExpect(status().isCreated());

        // Validate the MerchantStore in the database
        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMerchantStore() throws Exception {
        // Initialize the database
        merchantStoreService.save(merchantStore);

        int databaseSizeBeforeDelete = merchantStoreRepository.findAll().size();

        // Get the merchantStore
        restMerchantStoreMockMvc.perform(delete("/api/merchant-stores/{id}", merchantStore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean merchantStoreExistsInEs = merchantStoreSearchRepository.exists(merchantStore.getId());
        assertThat(merchantStoreExistsInEs).isFalse();

        // Validate the database is empty
        List<MerchantStore> merchantStoreList = merchantStoreRepository.findAll();
        assertThat(merchantStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMerchantStore() throws Exception {
        // Initialize the database
        merchantStoreService.save(merchantStore);

        // Search the merchantStore
        restMerchantStoreMockMvc.perform(get("/api/_search/merchant-stores?query=id:" + merchantStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(merchantStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].storeaddress").value(hasItem(DEFAULT_STOREADDRESS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].storename").value(hasItem(DEFAULT_STORENAME.toString())))
            .andExpect(jsonPath("$.[*].storeEmailAddress").value(hasItem(DEFAULT_STORE_EMAIL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].dEFAULTSTORE").value(hasItem(DEFAULT_D_EFAULTSTORE.toString())))
            .andExpect(jsonPath("$.[*].storephone").value(hasItem(DEFAULT_STOREPHONE.toString())))
            .andExpect(jsonPath("$.[*].weightunitcode").value(hasItem(DEFAULT_WEIGHTUNITCODE.toString())))
            .andExpect(jsonPath("$.[*].useCache").value(hasItem(DEFAULT_USE_CACHE.booleanValue())))
            .andExpect(jsonPath("$.[*].storeTemplate").value(hasItem(DEFAULT_STORE_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME.toString())))
            .andExpect(jsonPath("$.[*].invoiceTemplate").value(hasItem(DEFAULT_INVOICE_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].storeLogo").value(hasItem(DEFAULT_STORE_LOGO.toString())))
            .andExpect(jsonPath("$.[*].inBusinessSince").value(hasItem(DEFAULT_IN_BUSINESS_SINCE.toString())))
            .andExpect(jsonPath("$.[*].currencyFormatNational").value(hasItem(DEFAULT_CURRENCY_FORMAT_NATIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].storepostalcode").value(hasItem(DEFAULT_STOREPOSTALCODE.toString())))
            .andExpect(jsonPath("$.[*].seizeunitcode").value(hasItem(DEFAULT_SEIZEUNITCODE.toString())))
            .andExpect(jsonPath("$.[*].storestateprovince").value(hasItem(DEFAULT_STORESTATEPROVINCE.toString())))
            .andExpect(jsonPath("$.[*].continueshoppingurl").value(hasItem(DEFAULT_CONTINUESHOPPINGURL.toString())))
            .andExpect(jsonPath("$.[*].storecity").value(hasItem(DEFAULT_STORECITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MerchantStore.class);
    }
}
