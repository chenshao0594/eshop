package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerOptionDescription;
import com.smartshop.eshop.repository.CustomerOptionDescriptionRepository;
import com.smartshop.eshop.repository.search.CustomerOptionDescriptionSearchRepository;
import com.smartshop.eshop.service.CustomerOptionDescriptionService;

/**
 * Service Implementation for managing CustomerOptionDescription.
 */
@Service
@Transactional
public class CustomerOptionDescriptionServiceImpl extends AbstractDomainServiceImpl< CustomerOptionDescription, Long> implements CustomerOptionDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionDescriptionServiceImpl.class);
    private final CustomerOptionDescriptionRepository customerOptionDescriptionRepository;
    private final CustomerOptionDescriptionSearchRepository customerOptionDescriptionSearchRepository;
    
    public CustomerOptionDescriptionServiceImpl(CustomerOptionDescriptionRepository customerOptionDescriptionRepository, CustomerOptionDescriptionSearchRepository customerOptionDescriptionSearchRepository) {
        super(customerOptionDescriptionRepository,customerOptionDescriptionSearchRepository);
        this.customerOptionDescriptionRepository = customerOptionDescriptionRepository;
        this.customerOptionDescriptionSearchRepository = customerOptionDescriptionSearchRepository;
    }
    
}
