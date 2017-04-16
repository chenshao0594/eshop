package com.smartshop.eshop.web.rest;

import com.smartshop.eshop.EshopApp;

import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.repository.ProductImageRepository;
import com.smartshop.eshop.service.ProductImageService;
import com.smartshop.eshop.repository.search.ProductImageSearchRepository;
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
 * Test class for the ProductImageResource REST controller.
 *
 * @see ProductImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EshopApp.class)
public class ProductImageControllerIntTest {

    private static final String DEFAULT_PRODUCT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DEFAULT_IMAGE = false;
    private static final Boolean UPDATED_DEFAULT_IMAGE = true;

    private static final Integer DEFAULT_IMAGE_TYPE = 1;
    private static final Integer UPDATED_IMAGE_TYPE = 2;

    private static final Boolean DEFAULT_IMAGE_CROP = false;
    private static final Boolean UPDATED_IMAGE_CROP = true;

    private static final byte[] DEFAULT_IMAGE_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_CONTENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_CONTENT_TYPE = "image/png";

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductImageSearchRepository productImageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductImageMockMvc;

    private ProductImage productImage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductImageController productImageController = new ProductImageController(productImageService);
        this.restProductImageMockMvc = MockMvcBuilders.standaloneSetup(productImageController)
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
    public static ProductImage createEntity(EntityManager em) {
        ProductImage productImage = new ProductImage()
            .productImage(DEFAULT_PRODUCT_IMAGE)
            .productImageUrl(DEFAULT_PRODUCT_IMAGE_URL)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .imageType(DEFAULT_IMAGE_TYPE)
            .imageCrop(DEFAULT_IMAGE_CROP)
            .imageContent(DEFAULT_IMAGE_CONTENT)
            .imageContentContentType(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE);
        return productImage;
    }

    @Before
    public void initTest() {
        productImageSearchRepository.deleteAll();
        productImage = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductImage() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImage)))
            .andExpect(status().isCreated());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testProductImage.getProductImageUrl()).isEqualTo(DEFAULT_PRODUCT_IMAGE_URL);
        assertThat(testProductImage.isDefaultImage()).isEqualTo(DEFAULT_DEFAULT_IMAGE);
        assertThat(testProductImage.getImageType()).isEqualTo(DEFAULT_IMAGE_TYPE);
        assertThat(testProductImage.isImageCrop()).isEqualTo(DEFAULT_IMAGE_CROP);
        assertThat(testProductImage.getImageContent()).isEqualTo(DEFAULT_IMAGE_CONTENT);
        assertThat(testProductImage.getImageContentContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE);

        // Validate the ProductImage in Elasticsearch
        ProductImage productImageEs = productImageSearchRepository.findOne(testProductImage.getId());
        assertThat(productImageEs).isEqualToComparingFieldByField(testProductImage);
    }

    @Test
    @Transactional
    public void createProductImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productImageRepository.findAll().size();

        // Create the ProductImage with an existing ID
        productImage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImageContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = productImageRepository.findAll().size();
        // set the field null
        productImage.setImageContent(null);

        // Create the ProductImage, which fails.

        restProductImageMockMvc.perform(post("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImage)))
            .andExpect(status().isBadRequest());

        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductImages() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get all the productImageList
        restProductImageMockMvc.perform(get("/api/product-images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].productImage").value(hasItem(DEFAULT_PRODUCT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].productImageUrl").value(hasItem(DEFAULT_PRODUCT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageType").value(hasItem(DEFAULT_IMAGE_TYPE)))
            .andExpect(jsonPath("$.[*].imageCrop").value(hasItem(DEFAULT_IMAGE_CROP.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageContent").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_CONTENT))));
    }

    @Test
    @Transactional
    public void getProductImage() throws Exception {
        // Initialize the database
        productImageRepository.saveAndFlush(productImage);

        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", productImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productImage.getId().intValue()))
            .andExpect(jsonPath("$.productImage").value(DEFAULT_PRODUCT_IMAGE.toString()))
            .andExpect(jsonPath("$.productImageUrl").value(DEFAULT_PRODUCT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.imageType").value(DEFAULT_IMAGE_TYPE))
            .andExpect(jsonPath("$.imageCrop").value(DEFAULT_IMAGE_CROP.booleanValue()))
            .andExpect(jsonPath("$.imageContentContentType").value(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageContent").value(Base64Utils.encodeToString(DEFAULT_IMAGE_CONTENT)));
    }

    @Test
    @Transactional
    public void getNonExistingProductImage() throws Exception {
        // Get the productImage
        restProductImageMockMvc.perform(get("/api/product-images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductImage() throws Exception {
        // Initialize the database
        productImageService.save(productImage);

        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Update the productImage
        ProductImage updatedProductImage = productImageRepository.findOne(productImage.getId());
        updatedProductImage
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productImageUrl(UPDATED_PRODUCT_IMAGE_URL)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .imageType(UPDATED_IMAGE_TYPE)
            .imageCrop(UPDATED_IMAGE_CROP)
            .imageContent(UPDATED_IMAGE_CONTENT)
            .imageContentContentType(UPDATED_IMAGE_CONTENT_CONTENT_TYPE);

        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductImage)))
            .andExpect(status().isOk());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate);
        ProductImage testProductImage = productImageList.get(productImageList.size() - 1);
        assertThat(testProductImage.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testProductImage.getProductImageUrl()).isEqualTo(UPDATED_PRODUCT_IMAGE_URL);
        assertThat(testProductImage.isDefaultImage()).isEqualTo(UPDATED_DEFAULT_IMAGE);
        assertThat(testProductImage.getImageType()).isEqualTo(UPDATED_IMAGE_TYPE);
        assertThat(testProductImage.isImageCrop()).isEqualTo(UPDATED_IMAGE_CROP);
        assertThat(testProductImage.getImageContent()).isEqualTo(UPDATED_IMAGE_CONTENT);
        assertThat(testProductImage.getImageContentContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_CONTENT_TYPE);

        // Validate the ProductImage in Elasticsearch
        ProductImage productImageEs = productImageSearchRepository.findOne(testProductImage.getId());
        assertThat(productImageEs).isEqualToComparingFieldByField(testProductImage);
    }

    @Test
    @Transactional
    public void updateNonExistingProductImage() throws Exception {
        int databaseSizeBeforeUpdate = productImageRepository.findAll().size();

        // Create the ProductImage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductImageMockMvc.perform(put("/api/product-images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productImage)))
            .andExpect(status().isCreated());

        // Validate the ProductImage in the database
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProductImage() throws Exception {
        // Initialize the database
        productImageService.save(productImage);

        int databaseSizeBeforeDelete = productImageRepository.findAll().size();

        // Get the productImage
        restProductImageMockMvc.perform(delete("/api/product-images/{id}", productImage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean productImageExistsInEs = productImageSearchRepository.exists(productImage.getId());
        assertThat(productImageExistsInEs).isFalse();

        // Validate the database is empty
        List<ProductImage> productImageList = productImageRepository.findAll();
        assertThat(productImageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProductImage() throws Exception {
        // Initialize the database
        productImageService.save(productImage);

        // Search the productImage
        restProductImageMockMvc.perform(get("/api/_search/product-images?query=id:" + productImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].productImage").value(hasItem(DEFAULT_PRODUCT_IMAGE.toString())))
            .andExpect(jsonPath("$.[*].productImageUrl").value(hasItem(DEFAULT_PRODUCT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageType").value(hasItem(DEFAULT_IMAGE_TYPE)))
            .andExpect(jsonPath("$.[*].imageCrop").value(hasItem(DEFAULT_IMAGE_CROP.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageContent").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_CONTENT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductImage.class);
    }
}
