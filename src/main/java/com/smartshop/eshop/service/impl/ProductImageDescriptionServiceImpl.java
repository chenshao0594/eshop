package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductImageDescription;
import com.smartshop.eshop.repository.ProductImageDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductImageDescriptionSearchRepository;
import com.smartshop.eshop.service.ProductImageDescriptionService;

/**
 * Service Implementation for managing ProductImageDescription.
 */
@Service
@Transactional
public class ProductImageDescriptionServiceImpl extends AbstractDomainServiceImpl<ProductImageDescription, Long>
		implements ProductImageDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductImageDescriptionServiceImpl.class);
	private final ProductImageDescriptionRepository productImageDescriptionRepository;
	private final ProductImageDescriptionSearchRepository productImageDescriptionSearchRepository;

	public ProductImageDescriptionServiceImpl(ProductImageDescriptionRepository productImageDescriptionRepository,
			ProductImageDescriptionSearchRepository productImageDescriptionSearchRepository) {
		super(productImageDescriptionRepository, productImageDescriptionSearchRepository);
		this.productImageDescriptionRepository = productImageDescriptionRepository;
		this.productImageDescriptionSearchRepository = productImageDescriptionSearchRepository;
	}

}
