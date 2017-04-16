package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CountryDescription;
import com.smartshop.eshop.service.CountryDescriptionService;

/**
 * REST controller for managing CountryDescription.
 */
@RestController
@RequestMapping("/api/" + CountryDescriptionController.SECTION_KEY)
public class CountryDescriptionController extends AbstractDomainController<CountryDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(CountryDescriptionController.class);
	public static final String SECTION_KEY = "country-descriptions";
	private static final String ENTITY_NAME = "countryDescription";

	private final CountryDescriptionService countryDescriptionService;

	public CountryDescriptionController(CountryDescriptionService countryDescriptionService) {
		super(countryDescriptionService);
		this.countryDescriptionService = countryDescriptionService;
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