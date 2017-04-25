package com.smartshop.eshop.service;

import java.util.List;
import java.util.Map;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.CountryDescription;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing Country.
 */
public interface CountryService extends AbstractDomainService<Country, Long> {
	public Country getByCode(String code) throws BusinessException;

	public void addCountryDescription(Country country, CountryDescription description) throws BusinessException;

	public List<Country> getCountries(Language language) throws BusinessException;

	Map<String, Country> getCountriesMap(Language language) throws BusinessException;

	List<Country> getCountries(List<String> isoCodes, Language language) throws BusinessException;
}