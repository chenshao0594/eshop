package com.smartshop.eshop.repository;

import java.util.List;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductRelationship;

public interface ProductRelationshipRepositoryCustom {

	List<ProductRelationship> getByType(MerchantStore store, String type, Language language);

	List<ProductRelationship> getByType(MerchantStore store, String type, Product product, Language language);

	List<ProductRelationship> getByGroup(MerchantStore store, String group);

	List<ProductRelationship> getGroups(MerchantStore store);

	List<ProductRelationship> getByType(MerchantStore store, String type);

	List<ProductRelationship> listByProducts(Product product);

	List<ProductRelationship> getByType(MerchantStore store, String type, Product product);

}
