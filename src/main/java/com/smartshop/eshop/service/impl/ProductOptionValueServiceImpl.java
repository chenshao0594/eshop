package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.domain.ProductOptionValue;
import com.smartshop.eshop.domain.QProductOptionValue;
import com.smartshop.eshop.repository.ProductOptionValueRepository;
import com.smartshop.eshop.repository.search.ProductOptionValueSearchRepository;
import com.smartshop.eshop.service.ProductOptionValueService;

/**
 * Service Implementation for managing ProductOptionValue.
 */
@Service
@Transactional
public class ProductOptionValueServiceImpl extends AbstractDomainServiceImpl<ProductOptionValue, Long>
		implements ProductOptionValueService {

	private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionValueServiceImpl.class);
	private final ProductOptionValueRepository productOptionValueRepository;
	private final ProductOptionValueSearchRepository productOptionValueSearchRepository;

	public ProductOptionValueServiceImpl(ProductOptionValueRepository productOptionValueRepository,
			ProductOptionValueSearchRepository productOptionValueSearchRepository) {
		super(productOptionValueRepository, productOptionValueSearchRepository);
		this.productOptionValueRepository = productOptionValueRepository;
		this.productOptionValueSearchRepository = productOptionValueSearchRepository;
	}

	@Override
	public Page<ProductOptionValue> queryOptionValuesByOption(ProductOption productOption, Pageable pageable) {
		QProductOptionValue qProductOptionValue = QProductOptionValue.productOptionValue;
		return this.productOptionValueRepository.findAll(qProductOptionValue.productOption.eq(productOption), pageable);
	}

}
