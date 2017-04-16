package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.repository.ProductOptionRepository;
import com.smartshop.eshop.repository.search.ProductOptionSearchRepository;
import com.smartshop.eshop.service.ProductOptionService;

/**
 * Service Implementation for managing ProductOption.
 */
@Service
@Transactional
public class ProductOptionServiceImpl extends AbstractDomainServiceImpl<ProductOption, Long>
		implements ProductOptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
	private final ProductOptionRepository productOptionRepository;
	private final ProductOptionSearchRepository productOptionSearchRepository;

	public ProductOptionServiceImpl(ProductOptionRepository productOptionRepository,
			ProductOptionSearchRepository productOptionSearchRepository) {
		super(productOptionRepository, productOptionSearchRepository);
		this.productOptionRepository = productOptionRepository;
		this.productOptionSearchRepository = productOptionSearchRepository;
	}

}
