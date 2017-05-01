package com.smartshop.eshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.domain.ProductOptionValue;

/**
 * Service Interface for managing ProductOptionValue.
 */
public interface ProductOptionValueService extends AbstractDomainService<ProductOptionValue, Long> {

	public Page<ProductOptionValue> queryOptionValuesByOption(ProductOption productOption, Pageable pageable);

}