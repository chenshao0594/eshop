package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductOptionDescription;
import com.smartshop.eshop.repository.ProductOptionDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductOptionDescriptionSearchRepository;
import com.smartshop.eshop.service.ProductOptionDescriptionService;

/**
 * Service Implementation for managing ProductOptionDescription.
 */
@Service
@Transactional
public class ProductOptionDescriptionServiceImpl extends AbstractDomainServiceImpl<ProductOptionDescription, Long>
		implements ProductOptionDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionDescriptionServiceImpl.class);
	private final ProductOptionDescriptionRepository productOptionDescriptionRepository;
	private final ProductOptionDescriptionSearchRepository productOptionDescriptionSearchRepository;

	public ProductOptionDescriptionServiceImpl(ProductOptionDescriptionRepository productOptionDescriptionRepository,
			ProductOptionDescriptionSearchRepository productOptionDescriptionSearchRepository) {
		super(productOptionDescriptionRepository, productOptionDescriptionSearchRepository);
		this.productOptionDescriptionRepository = productOptionDescriptionRepository;
		this.productOptionDescriptionSearchRepository = productOptionDescriptionSearchRepository;
	}

}
