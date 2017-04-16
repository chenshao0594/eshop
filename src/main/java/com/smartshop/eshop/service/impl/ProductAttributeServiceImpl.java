package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.repository.ProductAttributeRepository;
import com.smartshop.eshop.repository.search.ProductAttributeSearchRepository;
import com.smartshop.eshop.service.ProductAttributeService;

/**
 * Service Implementation for managing ProductAttribute.
 */
@Service
@Transactional
public class ProductAttributeServiceImpl extends AbstractDomainServiceImpl<ProductAttribute, Long>
		implements ProductAttributeService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);
	private final ProductAttributeRepository productAttributeRepository;
	private final ProductAttributeSearchRepository productAttributeSearchRepository;

	public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository,
			ProductAttributeSearchRepository productAttributeSearchRepository) {
		super(productAttributeRepository, productAttributeSearchRepository);
		this.productAttributeRepository = productAttributeRepository;
		this.productAttributeSearchRepository = productAttributeSearchRepository;
	}

}
