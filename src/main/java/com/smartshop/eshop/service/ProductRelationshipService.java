package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.type.ProductRelationshipType;

/**
 * Service Interface for managing ProductRelationship.
 */
public interface ProductRelationshipService extends AbstractDomainService<ProductRelationship, Long> {

	void saveOrUpdate(ProductRelationship relationship) throws BusinessException;

	List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type,
			Language language) throws BusinessException;

	/**
	 * Get product relationship List for a given type (RELATED, FEATURED...) and
	 * a given base product
	 * 
	 * @param store
	 * @param product
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type)
			throws BusinessException;

	/**
	 * Get product relationship List for a given type (RELATED, FEATURED...)
	 * 
	 * @param store
	 * @param type
	 * @return
	 * @throws BusinessException
	 */
	List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type) throws BusinessException;

	List<ProductRelationship> listByProduct(Product product) throws BusinessException;

	List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type, Language language)
			throws BusinessException;

	/**
	 * Get a list of relationship acting as groups of products
	 * 
	 * @param store
	 * @return
	 */
	List<ProductRelationship> getGroups(MerchantStore store);

	/**
	 * Creates a product group
	 * 
	 * @param groupName
	 * @throws BusinessException
	 */
	void addGroup(MerchantStore store, String groupName) throws BusinessException;

	List<ProductRelationship> getByGroup(MerchantStore store, String groupName) throws BusinessException;

	void deleteGroup(MerchantStore store, String groupName) throws BusinessException;

	void deactivateGroup(MerchantStore store, String groupName) throws BusinessException;

	void activateGroup(MerchantStore store, String groupName) throws BusinessException;

	List<ProductRelationship> getByGroup(MerchantStore store, String groupName, Language language)
			throws BusinessException;

}