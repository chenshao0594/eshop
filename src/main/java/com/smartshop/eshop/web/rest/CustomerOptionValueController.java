package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOptionValue;
import com.smartshop.eshop.service.CustomerOptionValueService;

/**
 * REST controller for managing CustomerOptionValue.
 */
@RestController
@RequestMapping("/api/" + CustomerOptionValueController.SECTION_KEY)
public class CustomerOptionValueController extends AbstractDomainController<CustomerOptionValue, Long> {

	private final Logger log = LoggerFactory.getLogger(CustomerOptionValueController.class);
	public static final String SECTION_KEY = "customer-option-values";
	private static final String ENTITY_NAME = "customerOptionValue";

	private final CustomerOptionValueService customerOptionValueService;

	public CustomerOptionValueController(CustomerOptionValueService customerOptionValueService) {
		super(customerOptionValueService);
		this.customerOptionValueService = customerOptionValueService;
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