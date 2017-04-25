package com.smartshop.eshop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.domain.TaxRate;
import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.TaxRateRepository;
import com.smartshop.eshop.repository.search.TaxRateSearchRepository;
import com.smartshop.eshop.service.TaxRateService;

/**
 * Service Implementation for managing TaxRate.
 */
@Service
@Transactional
public class TaxRateServiceImpl extends AbstractDomainServiceImpl<TaxRate, Long> implements TaxRateService {

	private final Logger LOGGER = LoggerFactory.getLogger(TaxRateServiceImpl.class);
	private final TaxRateRepository taxRateRepository;
	private final TaxRateSearchRepository taxRateSearchRepository;

	public TaxRateServiceImpl(TaxRateRepository taxRateRepository, TaxRateSearchRepository taxRateSearchRepository) {
		super(taxRateRepository, taxRateSearchRepository);
		this.taxRateRepository = taxRateRepository;
		this.taxRateSearchRepository = taxRateSearchRepository;
	}

	@Override
	public List<TaxRate> listByStore(MerchantStore store) throws BusinessException {
		return taxRateRepository.findByStore(store.getId());
	}

	@Override
	public List<TaxRate> listByStore(MerchantStore store, Language language) throws BusinessException {
		return taxRateRepository.findByStoreAndLanguage(store.getId(), language.getId());
	}

	@Override
	public TaxRate getByCode(String code, MerchantStore store) throws BusinessException {
		return taxRateRepository.findByStoreAndCode(store.getId(), code);
	}

	@Override
	public List<TaxRate> listByCountryZoneAndTaxClass(Country country, Zone zone, TaxClass taxClass,
			MerchantStore store, Language language) throws BusinessException {
		// return taxRateDao.listByCountryZoneAndTaxClass(country, zone,
		// taxClass, store, language);
		return taxRateRepository.findByMerchantAndZoneAndCountryAndLanguage(store.getId(), zone.getId(),
				country.getId(), language.getId());
	}

	@Override
	public List<TaxRate> listByCountryStateProvinceAndTaxClass(Country country, String stateProvince, TaxClass taxClass,
			MerchantStore store, Language language) throws BusinessException {
		// return taxRateDao.listByCountryStateProvinceAndTaxClass(country,
		// stateProvince, taxClass, store, language);
		return taxRateRepository.findByMerchantAndProvinceAndCountryAndLanguage(store.getId(), stateProvince,
				country.getId(), language.getId());
	}

}
