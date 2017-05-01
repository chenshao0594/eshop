package com.smartshop.eshop.service;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.ProductOption;

/**
 * Service Interface for managing ProductOption.
 */
public interface ProductOptionService extends AbstractDomainService<ProductOption, Long> {
	ProductOption getByCode(MerchantStore store, String optionCode);

}