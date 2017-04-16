package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOptionDescription;
import com.smartshop.eshop.service.CustomerOptionDescriptionService;

/**
 * REST controller for managing CustomerOptionDescription.
 */
@RestController
@RequestMapping("/api/" + CustomerOptionDescriptionController.SECTION_KEY)
public class CustomerOptionDescriptionController extends AbstractDomainController<CustomerOptionDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(CustomerOptionDescriptionController.class);
	public static final String SECTION_KEY = "customer-option-descriptions";
	private static final String ENTITY_NAME = "customerOptionDescription";

	private final CustomerOptionDescriptionService customerOptionDescriptionService;

	public CustomerOptionDescriptionController(CustomerOptionDescriptionService customerOptionDescriptionService) {
		super(customerOptionDescriptionService);
		this.customerOptionDescriptionService = customerOptionDescriptionService;
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