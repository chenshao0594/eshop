package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Optin;
import com.smartshop.eshop.service.OptinService;

/**
 * REST controller for managing Optin.
 */
@RestController
@RequestMapping("/api/" + OptinController.SECTION_KEY)
public class OptinController extends AbstractDomainController<Optin, Long> {

	private final Logger log = LoggerFactory.getLogger(OptinController.class);
	public static final String SECTION_KEY = "optins";
	private static final String ENTITY_NAME = "optin";

	private final OptinService optinService;

	public OptinController(OptinService optinService) {
		super(optinService);
		this.optinService = optinService;
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