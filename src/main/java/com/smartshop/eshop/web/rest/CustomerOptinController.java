package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOptin;
import com.smartshop.eshop.service.CustomerOptinService;

/**
 * REST controller for managing CustomerOptin.
 */
@RestController
@RequestMapping("/api/" + CustomerOptinController.SECTION_KEY)
public class CustomerOptinController extends AbstractDomainController<CustomerOptin, Long> {

	private final Logger log = LoggerFactory.getLogger(CustomerOptinController.class);
	public static final String SECTION_KEY = "customer-optins";
	private static final String ENTITY_NAME = "customerOptin";

	private final CustomerOptinService customerOptinService;

	public CustomerOptinController(CustomerOptinService customerOptinService) {
		super(customerOptinService);
		this.customerOptinService = customerOptinService;
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