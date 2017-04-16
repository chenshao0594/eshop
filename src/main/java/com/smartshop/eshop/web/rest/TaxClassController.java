package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.service.TaxClassService;

/**
 * REST controller for managing TaxClass.
 */
@RestController
@RequestMapping("/api/" + TaxClassController.SECTION_KEY)
public class TaxClassController extends AbstractDomainController<TaxClass, Long> {

	private final Logger log = LoggerFactory.getLogger(TaxClassController.class);
	public static final String SECTION_KEY = "tax-classes";
	private static final String ENTITY_NAME = "taxClass";

	private final TaxClassService taxClassService;

	public TaxClassController(TaxClassService taxClassService) {
		super(taxClassService);
		this.taxClassService = taxClassService;
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