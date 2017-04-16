package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductPrice;
import com.smartshop.eshop.repository.ProductPriceRepository;
import com.smartshop.eshop.service.ProductPriceService;
import com.smartshop.eshop.repository.search.ProductPriceSearchRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartshop.eshop.domain.enumeration.ProductPriceType;
/**
 * Test class for the ProductPriceResource REST controller.
 *
 * @see ProductPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductPriceControllerIntTest {

    private static final Boolean DEFAULT_DEFAULT_PRICE = false;
    private static final Boolean UPDATED_DEFAULT_PRICE = true;

    private static final String DEFAULT_D_EFAULTPRICECODE = "AAAAAAAAAA";
    private static final String UPDATED_D_EFAULTPRICECODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_PRICE_SPECIAL_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PRODUCT_PRICE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE_SPECIAL_AMOUNT = new BigDecimal(2);

    private static final ProductPriceType DEFAULT_PRODUCT_PRICE_TYPE = ProductPriceType.ONE_TIME;
    private static final ProductPriceType UPDATED_PRODUCT_PRICE_TYPE = ProductPriceType.MONTHLY;

    private static final LocalDate DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_PRICE_SPECIAL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ProductPriceSearchRepository productPriceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductPriceMockMvc;

    private ProductPrice productPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductPriceController productPriceController = new ProductPriceController(productPriceService);
        this.restProductPriceMockMvc = MockMvcBuilders.standaloneSetup(productPriceController)
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
    public static ProductPrice createEntity(EntityManager em) {
        ProductPrice productPrice = new ProductPrice()
            .defaultPrice(DEFAULT_DEFAULT_PRICE)
            .dEFAULTPRICECODE(DEFAULT_D_EFAULTPRICECODE)
            .code(DEFAULT_CODE)
            .productPriceAmount(DEFAULT_PRODUCT_PRICE_AMOUNT)
            .productPriceSpecialAmount(DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT)
            .productPriceType(DEFAULT_PRODUCT_PRICE_TYPE);
        return productPrice;
    }

    @Before
    public void initTest() {
        productPriceSearchRepository.deleteAll();
        productPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPrice() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();

        // Create the ProductPrice
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isCreated());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.isDefaultPrice()).isEqualTo(DEFAULT_DEFAULT_PRICE);
        assertThat(testProductPrice.getdEFAULTPRICECODE()).isEqualTo(DEFAULT_D_EFAULTPRICECODE);
        assertThat(testProductPrice.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductPrice.getProductPriceSpecialEndDate()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE);
        assertThat(testProductPrice.getProductPriceAmount()).isEqualTo(DEFAULT_PRODUCT_PRICE_AMOUNT);
        assertThat(testProductPrice.getProductPriceSpecialAmount()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT);
        assertThat(testProductPrice.getProductPriceType()).isEqualTo(DEFAULT_PRODUCT_PRICE_TYPE);
        assertThat(testProductPrice.getProductPriceSpecialStartDate()).isEqualTo(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE);

        // Validate the ProductPrice in Elasticsearch
        ProductPrice productPriceEs = productPriceSearchRepository.findOne(testProductPrice.getId());
        assertThat(productPriceEs).isEqualToComparingFieldByField(testProductPrice);
    }

    @Test
    @Transactional
    public void createProductPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();

        // Create the ProductPrice with an existing ID
        productPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productPriceRepository.findAll().size();
        // set the field null
        productPrice.setCode(null);

        // Create the ProductPrice, which fails.

        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isBadRequest());

        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductPrices() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultPrice").value(hasItem(DEFAULT_DEFAULT_PRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].dEFAULTPRICECODE").value(hasItem(DEFAULT_D_EFAULTPRICECODE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialEndDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceAmount").value(hasItem(DEFAULT_PRODUCT_PRICE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].productPriceSpecialAmount").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].productPriceType").value(hasItem(DEFAULT_PRODUCT_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialStartDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void getProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", productPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productPrice.getId().intValue()))
            .andExpect(jsonPath("$.defaultPrice").value(DEFAULT_DEFAULT_PRICE.booleanValue()))
            .andExpect(jsonPath("$.dEFAULTPRICECODE").value(DEFAULT_D_EFAULTPRICECODE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.productPriceSpecialEndDate").value(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString()))
            .andExpect(jsonPath("$.productPriceAmount").value(DEFAULT_PRODUCT_PRICE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.productPriceSpecialAmount").value(DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.productPriceType").value(DEFAULT_PRODUCT_PRICE_TYPE.toString()))
            .andExpect(jsonPath("$.productPriceSpecialStartDate").value(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductPrice() throws Exception {
        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPrice() throws Exception {
        // Initialize the database
        productPriceService.save(productPrice);

        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // Update the productPrice
        ProductPrice updatedProductPrice = productPriceRepository.findOne(productPrice.getId());
        updatedProductPrice
            .defaultPrice(UPDATED_DEFAULT_PRICE)
            .dEFAULTPRICECODE(UPDATED_D_EFAULTPRICECODE)
            .code(UPDATED_CODE)
            .productPriceAmount(UPDATED_PRODUCT_PRICE_AMOUNT)
            .productPriceSpecialAmount(UPDATED_PRODUCT_PRICE_SPECIAL_AMOUNT)
            .productPriceType(UPDATED_PRODUCT_PRICE_TYPE);

        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductPrice)))
            .andExpect(status().isOk());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.isDefaultPrice()).isEqualTo(UPDATED_DEFAULT_PRICE);
        assertThat(testProductPrice.getdEFAULTPRICECODE()).isEqualTo(UPDATED_D_EFAULTPRICECODE);
        assertThat(testProductPrice.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductPrice.getProductPriceSpecialEndDate()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL_END_DATE);
        assertThat(testProductPrice.getProductPriceAmount()).isEqualTo(UPDATED_PRODUCT_PRICE_AMOUNT);
        assertThat(testProductPrice.getProductPriceSpecialAmount()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL_AMOUNT);
        assertThat(testProductPrice.getProductPriceType()).isEqualTo(UPDATED_PRODUCT_PRICE_TYPE);
        assertThat(testProductPrice.getProductPriceSpecialStartDate()).isEqualTo(UPDATED_PRODUCT_PRICE_SPECIAL_START_DATE);

        // Validate the ProductPrice in Elasticsearch
        ProductPrice productPriceEs = productPriceSearchRepository.findOne(testProductPrice.getId());
        assertThat(productPriceEs).isEqualToComparingFieldByField(testProductPrice);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPrice() throws Exception {
        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // Create the ProductPrice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isCreated());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductPrice() throws Exception {
        // Initialize the database
        productPriceService.save(productPrice);

        int databaseSizeBeforeDelete = productPriceRepository.findAll().size();

        // Get the productPrice
        restProductPriceMockMvc.perform(delete("/api/product-prices/{id}", productPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productPriceExistsInEs = productPriceSearchRepository.exists(productPrice.getId());
        assertThat(productPriceExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductPrice() throws Exception {
        // Initialize the database
        productPriceService.save(productPrice);

        // Search the productPrice
        restProductPriceMockMvc.perform(get("/api/_search/product-prices?query=id:" + productPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].defaultPrice").value(hasItem(DEFAULT_DEFAULT_PRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].dEFAULTPRICECODE").value(hasItem(DEFAULT_D_EFAULTPRICECODE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialEndDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].productPriceAmount").value(hasItem(DEFAULT_PRODUCT_PRICE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].productPriceSpecialAmount").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].productPriceType").value(hasItem(DEFAULT_PRODUCT_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].productPriceSpecialStartDate").value(hasItem(DEFAULT_PRODUCT_PRICE_SPECIAL_START_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductPrice.class);
    }
}
