package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOption;
import com.smartshop.eshop.service.CustomerOptionService;

/**
 * REST controller for managing CustomerOption.
 */
@RestController
@RequestMapping("/api/" + CustomerOptionController.SECTION_KEY)
public class CustomerOptionController extends AbstractDomainController<CustomerOption, Long> {

	private final Logger log = LoggerFactory.getLogger(CustomerOptionController.class);
	public static final String SECTION_KEY = "customer-options";
	private static final String ENTITY_NAME = "customerOption";

	private final CustomerOptionService customerOptionService;

	public CustomerOptionController(CustomerOptionService customerOptionService) {
		super(customerOptionService);
		this.customerOptionService = customerOptionService;
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