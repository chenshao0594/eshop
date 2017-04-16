package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.TaxRateDescription;
import com.smartshop.eshop.service.TaxRateDescriptionService;

/**
 * REST controller for managing TaxRateDescription.
 */
@RestController
@RequestMapping("/api/" + TaxRateDescriptionController.SECTION_KEY)
public class TaxRateDescriptionController extends AbstractDomainController<TaxRateDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(TaxRateDescriptionController.class);
	public static final String SECTION_KEY = "tax-rate-descriptions";
	private static final String ENTITY_NAME = "taxRateDescription";

	private final TaxRateDescriptionService taxRateDescriptionService;

	public TaxRateDescriptionController(TaxRateDescriptionService taxRateDescriptionService) {
		super(taxRateDescriptionService);
		this.taxRateDescriptionService = taxRateDescriptionService;
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