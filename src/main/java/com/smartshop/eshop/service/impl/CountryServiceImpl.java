package com.smartshop.eshop.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.CountryDescription;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.CountryRepository;
import com.smartshop.eshop.repository.search.CountrySearchRepository;
import com.smartshop.eshop.service.CountryService;

/**
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl extends AbstractDomainServiceImpl<Country, Long> implements CountryService {

	private final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
	private final CountryRepository countryRepository;
	private final CountrySearchRepository countrySearchRepository;

	public CountryServiceImpl(CountryRepository countryRepository, CountrySearchRepository countrySearchRepository) {
		super(countryRepository, countrySearchRepository);
		this.countryRepository = countryRepository;
		this.countrySearchRepository = countrySearchRepository;
	}

	@Override
	public void addCountryDescription(Country country, CountryDescription description) throws BusinessException {
		country.getDescriptions().add(description);
		description.setCountry(country);
		update(country);
	}

	@Override
	public Map<String, Country> getCountriesMap(Language language) throws BusinessException {

		List<Country> countries = this.getCountries(language);

		Map<String, Country> returnMap = new LinkedHashMap<String, Country>();

		for (Country country : countries) {
			returnMap.put(country.getIsoCode(), country);
		}

		return returnMap;
	}

	@Override
	public List<Country> getCountries(final List<String> isoCodes, final Language language) throws BusinessException {
		List<Country> countryList = getCountries(language);
		List<Country> requestedCountryList = new ArrayList<Country>();
		if (!CollectionUtils.isEmpty(countryList)) {
			for (Country c : countryList) {
				if (isoCodes.contains(c.getIsoCode())) {
					requestedCountryList.add(c);
				}
			}
		}
		return requestedCountryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountries(Language language) throws BusinessException {

		List<Country> countries = null;
		try {
			countries = countryRepository.listByLanguage(language.getId());
			// set names
			for (Country country : countries) {
				CountryDescription description = (CountryDescription) country.getDescriptions().toArray()[0];
				country.setName(description.getName());
			}
		} catch (

		Exception e) {
			LOGGER.error("getCountries()", e);
		}

		return countries;

	}

	@Override
	public Country getByCode(String code) throws BusinessException {
		return countryRepository.findByIsoCode(code);
	}

}
