package com.smartshop.eshop.service;

import java.util.List;
import java.util.Locale;

import org.hibernate.service.spi.ServiceException;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.domain.TaxClass;

/**
 * Service Interface for managing Product.
 */
public interface ProductService extends AbstractDomainService< Product, Long>{
void addProductDescription(Product product, ProductDescription description) throws ServiceException;
	
	ProductDescription getProductDescription(Product product, Language language);
	
	Product getProductForLocale(long productId, Language language, Locale locale) throws ServiceException;
	
	List<Product> getProductsForLocale(Category category, Language language, Locale locale) throws ServiceException;

	List<Product> getProducts(List<Long> categoryIds) throws ServiceException;

	List<Product> listByStore(MerchantStore store, Language language);

	List<Product> listByStore(MerchantStore store);

	List<Product> listByTaxClass(TaxClass taxClass);

	List<Product> getProducts(List<Long> categoryIds, Language language)
			throws ServiceException;

	Product getBySeUrl(MerchantStore store, String seUrl, Locale locale);

	/**
	 * Get a product by sku (code) field  and the language
	 * @param productCode
	 * @param language
	 * @return
	 */
	Product getByCode(String productCode, Language language);

	void create(Product product) throws ServiceException;

     }