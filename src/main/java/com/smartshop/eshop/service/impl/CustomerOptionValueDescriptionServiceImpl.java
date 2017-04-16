package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;
import com.smartshop.eshop.repository.CustomerOptionValueDescriptionRepository;
import com.smartshop.eshop.repository.search.CustomerOptionValueDescriptionSearchRepository;
import com.smartshop.eshop.service.CustomerOptionValueDescriptionService;

/**
 * Service Implementation for managing CustomerOptionValueDescription.
 */
@Service
@Transactional
public class CustomerOptionValueDescriptionServiceImpl
		extends AbstractDomainServiceImpl<CustomerOptionValueDescription, Long>
		implements CustomerOptionValueDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionValueDescriptionServiceImpl.class);
	private final CustomerOptionValueDescriptionRepository customerOptionValueDescriptionRepository;
	private final CustomerOptionValueDescriptionSearchRepository customerOptionValueDescriptionSearchRepository;

	public CustomerOptionValueDescriptionServiceImpl(
			CustomerOptionValueDescriptionRepository customerOptionValueDescriptionRepository,
			CustomerOptionValueDescriptionSearchRepository customerOptionValueDescriptionSearchRepository) {
		super(customerOptionValueDescriptionRepository, customerOptionValueDescriptionSearchRepository);
		this.customerOptionValueDescriptionRepository = customerOptionValueDescriptionRepository;
		this.customerOptionValueDescriptionSearchRepository = customerOptionValueDescriptionSearchRepository;
	}

}
