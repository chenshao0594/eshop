package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerOptionSet;
import com.smartshop.eshop.repository.CustomerOptionSetRepository;
import com.smartshop.eshop.repository.search.CustomerOptionSetSearchRepository;
import com.smartshop.eshop.service.CustomerOptionSetService;

/**
 * Service Implementation for managing CustomerOptionSet.
 */
@Service
@Transactional
public class CustomerOptionSetServiceImpl extends AbstractDomainServiceImpl<CustomerOptionSet, Long>
		implements CustomerOptionSetService {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionSetServiceImpl.class);
	private final CustomerOptionSetRepository customerOptionSetRepository;
	private final CustomerOptionSetSearchRepository customerOptionSetSearchRepository;

	public CustomerOptionSetServiceImpl(CustomerOptionSetRepository customerOptionSetRepository,
			CustomerOptionSetSearchRepository customerOptionSetSearchRepository) {
		super(customerOptionSetRepository, customerOptionSetSearchRepository);
		this.customerOptionSetRepository = customerOptionSetRepository;
		this.customerOptionSetSearchRepository = customerOptionSetSearchRepository;
	}

}
