package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerOptin;
import com.smartshop.eshop.repository.CustomerOptinRepository;
import com.smartshop.eshop.repository.search.CustomerOptinSearchRepository;
import com.smartshop.eshop.service.CustomerOptinService;

/**
 * Service Implementation for managing CustomerOptin.
 */
@Service
@Transactional
public class CustomerOptinServiceImpl extends AbstractDomainServiceImpl<CustomerOptin, Long>
		implements CustomerOptinService {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptinServiceImpl.class);
	private final CustomerOptinRepository customerOptinRepository;
	private final CustomerOptinSearchRepository customerOptinSearchRepository;

	public CustomerOptinServiceImpl(CustomerOptinRepository customerOptinRepository,
			CustomerOptinSearchRepository customerOptinSearchRepository) {
		super(customerOptinRepository, customerOptinSearchRepository);
		this.customerOptinRepository = customerOptinRepository;
		this.customerOptinSearchRepository = customerOptinSearchRepository;
	}

}
