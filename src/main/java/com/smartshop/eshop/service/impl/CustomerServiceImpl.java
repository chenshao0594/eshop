package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerService;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.repository.CustomerRepository;
import com.smartshop.eshop.repository.search.CustomerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing Customer.
 */
@Service
@Transactional
public class CustomerServiceImpl extends AbstractDomainServiceImpl<Customer, Long> implements CustomerService {

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	private final CustomerRepository customerRepository;
	private final CustomerSearchRepository customerSearchRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository,
			CustomerSearchRepository customerSearchRepository) {
		super(customerRepository, customerSearchRepository);
		this.customerRepository = customerRepository;
		this.customerSearchRepository = customerSearchRepository;
	}

}
