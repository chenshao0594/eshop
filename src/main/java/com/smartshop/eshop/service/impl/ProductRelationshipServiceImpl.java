package com.smartshop.eshop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.ProductRelationshipRepository;
import com.smartshop.eshop.repository.search.ProductRelationshipSearchRepository;
import com.smartshop.eshop.service.ProductRelationshipService;
import com.smartshop.eshop.type.ProductRelationshipType;

/**
 * Service Implementation for managing ProductRelationship.
 */
@Service
@Transactional
public class ProductRelationshipServiceImpl extends AbstractDomainServiceImpl<ProductRelationship, Long>
		implements ProductRelationshipService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductRelationshipServiceImpl.class);
	private final ProductRelationshipRepository productRelationshipRepository;
	private final ProductRelationshipSearchRepository productRelationshipSearchRepository;

	public ProductRelationshipServiceImpl(ProductRelationshipRepository productRelationshipRepository,
			ProductRelationshipSearchRepository productRelationshipSearchRepository) {
		super(productRelationshipRepository, productRelationshipSearchRepository);
		this.productRelationshipRepository = productRelationshipRepository;
		this.productRelationshipSearchRepository = productRelationshipSearchRepository;
	}

	@Override
	public void saveOrUpdate(ProductRelationship relationship) throws BusinessException {

		if (relationship.getId() != null && relationship.getId() > 0) {

			this.update(relationship);

		} else {
			this.save(relationship);
		}

	}

	@Override
	public void addGroup(MerchantStore store, String groupName) throws BusinessException {
		ProductRelationship relationship = new ProductRelationship();
		relationship.setCode(groupName);
		relationship.setStore(store);
		relationship.setActive(true);
		this.save(relationship);
	}

	@Override
	public List<ProductRelationship> getGroups(MerchantStore store) {
		return productRelationshipRepository.getGroups(store);
	}

	@Override
	public void deleteGroup(MerchantStore store, String groupName) throws BusinessException {
		List<ProductRelationship> entities = productRelationshipRepository.getByGroup(store, groupName);
		for (ProductRelationship relation : entities) {
			this.delete(relation);
		}
	}

	@Override
	public void deactivateGroup(MerchantStore store, String groupName) throws BusinessException {
		List<ProductRelationship> entities = productRelationshipRepository.getByGroup(store, groupName);
		for (ProductRelationship relation : entities) {
			relation.setActive(false);
			this.saveOrUpdate(relation);
		}
	}

	@Override
	public void activateGroup(MerchantStore store, String groupName) throws BusinessException {
		List<ProductRelationship> entities = this.getByGroup(store, groupName);
		for (ProductRelationship relation : entities) {
			relation.setActive(true);
			this.saveOrUpdate(relation);
		}
	}

	@Override
	public List<ProductRelationship> listByProduct(Product product) throws BusinessException {

		return productRelationshipRepository.listByProducts(product);

	}

	@Override
	public List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type,
			Language language) throws BusinessException {

		return productRelationshipRepository.getByType(store, type.name(), product, language);

	}

	@Override
	public List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type, Language language)
			throws BusinessException {
		return productRelationshipRepository.getByType(store, type.name(), language);
	}

	@Override
	public List<ProductRelationship> getByType(MerchantStore store, ProductRelationshipType type)
			throws BusinessException {

		return productRelationshipRepository.getByType(store, type.name());

	}

	@Override
	public List<ProductRelationship> getByGroup(MerchantStore store, String groupName) throws BusinessException {

		return productRelationshipRepository.getByType(store, groupName);

	}

	@Override
	public List<ProductRelationship> getByGroup(MerchantStore store, String groupName, Language language)
			throws BusinessException {

		return productRelationshipRepository.getByType(store, groupName, language);

	}

	@Override
	public List<ProductRelationship> getByType(MerchantStore store, Product product, ProductRelationshipType type)
			throws BusinessException {

		return productRelationshipRepository.getByType(store, type.name(), product);

	}

}
