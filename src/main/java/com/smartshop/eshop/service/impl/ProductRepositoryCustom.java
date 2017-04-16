package com.smartshop.eshop.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.TaxClass;

public interface ProductRepositoryCustom {
	

	Product getByFriendlyUrl(MerchantStore store,String seUrl, Locale locale);

	List<Product> getProductsListByCategories(@SuppressWarnings("rawtypes") Set categoryIds);

	List<Product> getProductsListByCategories(Set<Long> categoryIds,
			Language language);

	List<Product> listByTaxClass(TaxClass taxClass);

	List<Product> listByStore(MerchantStore store);

	Product getProductForLocale(long productId, Language language,
			Locale locale);

	Product getById(Long productId);

	Product getByCode(String productCode, Language language);

	List<Product> getProductsForLocale(MerchantStore store,
			Set<Long> categoryIds, Language language, Locale locale);

}
