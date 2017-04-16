package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductPriceDescription;
import com.smartshop.eshop.repository.ProductPriceDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductPriceDescriptionSearchRepository;
import com.smartshop.eshop.service.ProductPriceDescriptionService;

/**
 * Service Implementation for managing ProductPriceDescription.
 */
@Service
@Transactional
public class ProductPriceDescriptionServiceImpl extends AbstractDomainServiceImpl<ProductPriceDescription, Long>
		implements ProductPriceDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductPriceDescriptionServiceImpl.class);
	private final ProductPriceDescriptionRepository productPriceDescriptionRepository;
	private final ProductPriceDescriptionSearchRepository productPriceDescriptionSearchRepository;

	public ProductPriceDescriptionServiceImpl(ProductPriceDescriptionRepository productPriceDescriptionRepository,
			ProductPriceDescriptionSearchRepository productPriceDescriptionSearchRepository) {
		super(productPriceDescriptionRepository, productPriceDescriptionSearchRepository);
		this.productPriceDescriptionRepository = productPriceDescriptionRepository;
		this.productPriceDescriptionSearchRepository = productPriceDescriptionSearchRepository;
	}

}
