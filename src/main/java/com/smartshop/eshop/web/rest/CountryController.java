package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.service.CountryService;

/**
 * REST controller for managing Country.
 */
@RestController
@RequestMapping("/api/" + CountryController.SECTION_KEY)
public class CountryController extends AbstractDomainController<Country, Long> {

	private final Logger log = LoggerFactory.getLogger(CountryController.class);
	public static final String SECTION_KEY = "countries";
	private static final String ENTITY_NAME = "country";

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		super(countryService);
		this.countryService = countryService;
	}

	@Override
	protected String getSectionKey() {
		return SECTION_KEY;
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

}