package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing TaxClass.
 */
public interface TaxClassService extends AbstractDomainService<TaxClass, Long> {
	public List<TaxClass> listByStore(MerchantStore store) throws BusinessException;

	TaxClass getByCode(String code) throws BusinessException;

	TaxClass getByCode(String code, MerchantStore store) throws BusinessException;

}