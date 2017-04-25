package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.domain.TaxRate;
import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing TaxRate.
 */
public interface TaxRateService extends AbstractDomainService<TaxRate, Long> {
	public List<TaxRate> listByStore(MerchantStore store) throws BusinessException;

	List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone, TaxClass taxClass, MerchantStore store,
			Language language) throws BusinessException;

	List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country, String stateProvince, TaxClass taxClass,
			MerchantStore store, Language language) throws BusinessException;

	TaxRate getByCode(String code, MerchantStore store) throws BusinessException;

	List<TaxRate> listByStore(MerchantStore store, Language language) throws BusinessException;

}