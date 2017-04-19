package com.smartshop.eshop.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.catalog.helper.CatalogServiceHelper;
import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.ProductRepository;
import com.smartshop.eshop.repository.search.ProductSearchRepository;
import com.smartshop.eshop.service.CategoryService;
import com.smartshop.eshop.service.ProductAttributeService;
import com.smartshop.eshop.service.ProductAvailabilityService;
import com.smartshop.eshop.service.ProductImageService;
import com.smartshop.eshop.service.ProductOptionService;
import com.smartshop.eshop.service.ProductOptionValueService;
import com.smartshop.eshop.service.ProductPriceService;
import com.smartshop.eshop.service.ProductRelationshipService;
import com.smartshop.eshop.service.ProductReviewService;
import com.smartshop.eshop.service.ProductService;
import com.smartshop.eshop.temp.ProductCriteria;
import com.smartshop.eshop.temp.ProductList;
import com.smartshop.eshop.type.FileContentType;
import com.smartshop.eshop.type.ImageContentFile;
import com.smartshop.eshop.utils.CoreConfiguration;

/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractDomainServiceImpl<Product, Long> implements ProductService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	private final ProductRepository productRepository;
	private final ProductSearchRepository productSearchRepository;
	@Inject
	CategoryService categoryService;
	@Inject
	ProductAvailabilityService productAvailabilityService;

	@Inject
	ProductPriceService productPriceService;

	@Inject
	ProductOptionService productOptionService;

	@Inject
	ProductOptionValueService productOptionValueService;

	@Inject
	ProductAttributeService productAttributeService;

	@Inject
	ProductRelationshipService productRelationshipService;

	@Inject
	ProductImageService productImageService;

	@Inject
	CoreConfiguration configuration;

	@Inject
	ProductReviewService productReviewService;

	public ProductServiceImpl(ProductRepository productRepository, ProductSearchRepository productSearchRepository) {
		super(productRepository, productSearchRepository);
		this.productRepository = productRepository;
		this.productSearchRepository = productSearchRepository;
	}

	@Override
	public void addProductDescription(Product product, ProductDescription description) throws ServiceException {

		if (product.getDescriptions() == null) {
			product.setDescriptions(new HashSet<ProductDescription>());
		}

		product.getDescriptions().add(description);
		description.setProduct(product);
		update(product);
		productSearchRepository.index(product);
	}

	@Override
	public List<Product> getProducts(List<Long> categoryIds) throws ServiceException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set ids = new HashSet(categoryIds);
		return productRepository.getProductsListByCategories(ids);
	}

	@Override
	public Product getById(Long productId) {
		return productRepository.getById(productId);
	}

	@Override
	public List<Product> getProducts(List<Long> categoryIds, Language language) throws ServiceException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Set<Long> ids = new HashSet(categoryIds);
		return productRepository.getProductsListByCategories(ids, language);

	}

	@Override
	public ProductDescription getProductDescription(Product product, Language language) {
		for (ProductDescription description : product.getDescriptions()) {
			if (description.getLanguage().equals(language)) {
				return description;
			}
		}
		return null;
	}

	@Override
	public Product getBySeUrl(MerchantStore store, String seUrl, Locale locale) {
		return productRepository.getByFriendlyUrl(store, seUrl, locale);
	}

	@Override
	public Product getProductForLocale(long productId, Language language, Locale locale) throws ServiceException {
		Product product = productRepository.getProductForLocale(productId, language, locale);

		CatalogServiceHelper.setToAvailability(product, locale);
		CatalogServiceHelper.setToLanguage(product, language.getId());
		return product;
	}

	@Override
	public List<Product> getProductsForLocale(Category category, Language language, Locale locale)
			throws ServiceException {

		if (category == null) {
			throw new ServiceException("The category is null");
		}

		// Get the category list
		StringBuilder lineage = new StringBuilder().append(category.getLineage()).append(category.getId()).append("/");
		List<Category> categories = categoryService.listByLineage(category.getMerchantStore(), lineage.toString());
		Set<Long> categoryIds = new HashSet<Long>();
		for (Category c : categories) {
			categoryIds.add(c.getId());
		}
		categoryIds.add(category.getId());
		// Get products
		List<Product> products = productRepository.getProductsForLocale(category.getMerchantStore(), categoryIds,
				language, locale);
		return products;
	}

	@Override
	public ProductList listByStore(MerchantStore store, Language language, ProductCriteria criteria) {
		return productRepository.listByStore(store, language, criteria);
	}

	@Override
	public List<Product> listByStore(MerchantStore store) {
		return productRepository.listByStore(store);
	}

	@Override
	public List<Product> listByTaxClass(TaxClass taxClass) {
		return productRepository.listByTaxClass(taxClass);
	}

	@Override
	public Product getByCode(String productCode, Language language) {
		return productRepository.getByCode(productCode, language);
	}

	@Override
	public void delete(Product product) throws ServiceException {
		LOGGER.debug("Deleting product");
		Validate.notNull(product, "Product cannot be null");
		Validate.notNull(product.getMerchantStore(), "MerchantStore cannot be null in product");
		product = this.getById(product.getId());// Prevents detached entity
		product.setCategories(null);

		Set<ProductImage> images = product.getImages();

		for (ProductImage image : images) {
			try {
				productImageService.removeProductImage(image);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		product.setImages(null);
		// delete reviews
		List<ProductReview> reviews = productReviewService.getByProductNoCustomers(product);
		for (ProductReview review : reviews) {
			productReviewService.delete(review);
		}
		// related - featured
		List<ProductRelationship> relationships = null;
		try {
			relationships = productRelationshipService.listByProduct(product);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (ProductRelationship relationship : relationships) {
			productRelationshipService.delete(relationship);
		}
		super.delete(product);
		productSearchRepository.delete(product);
	}

	@Override
	public Product save(Product product) {
		this.saveOrUpdate(product);
		productSearchRepository.save(product);
		return product;
	}

	@Override
	public void update(Product product) throws ServiceException {
		this.saveOrUpdate(product);
		productSearchRepository.index(product);
	}

	private void saveOrUpdate(Product product) throws ServiceException {
		LOGGER.debug("Save or update product ");
		Validate.notNull(product, "product cannot be null");
		//Validate.notNull(product.getAvailabilities(), "product must have at least one availability");
		//Validate.notEmpty(product.getAvailabilities(), "product must have at least one availability");
		// List of original images
		Set<ProductImage> originalProductImages = null;
		if (product.getId() != null && product.getId() > 0) {
			originalProductImages = product.getImages();
		}
		/** save product first **/
		super.save(product);
		/**
		 * Image creation needs extra service to save the file in the CMS
		 */
		List<Long> newImageIds = new ArrayList<Long>();
		Set<ProductImage> images = product.getImages();
		try {
			if (images != null && images.size() > 0) {
				for (ProductImage image : images) {
					if (image.getImage() != null && (image.getId() == null || image.getId() == 0L)) {
						image.setProduct(product);
						InputStream inputStream = image.getImage();
						ImageContentFile cmsContentImage = new ImageContentFile();
						cmsContentImage.setFileName(image.getProductImage());
						cmsContentImage.setFile(inputStream);
						cmsContentImage.setFileContentType(FileContentType.PRODUCT);
						productImageService.addProductImage(product, image, cmsContentImage);
						newImageIds.add(image.getId());
					} else {
						productImageService.save(image);
						newImageIds.add(image.getId());
					}
				}
			}
			// cleanup old images
			if (originalProductImages != null) {
				for (ProductImage image : originalProductImages) {
					if (!newImageIds.contains(image.getId())) {
						productImageService.delete(image);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Cannot save images " + e.getMessage());
		}

	}

}
