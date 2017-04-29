package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.domain.ProductOptionValue;

/**
 * Service Interface for managing ProductOptionValue.
 */
public interface ProductOptionValueService extends AbstractDomainService<ProductOptionValue, Long> {

	public List<ProductOptionValue> queryOptionValuesByOption(ProductOption productOption);

}