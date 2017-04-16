package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerOption;
import com.smartshop.eshop.repository.CustomerOptionRepository;
import com.smartshop.eshop.repository.search.CustomerOptionSearchRepository;
import com.smartshop.eshop.service.CustomerOptionService;

/**
 * Service Implementation for managing CustomerOption.
 */
@Service
@Transactional
public class CustomerOptionServiceImpl extends AbstractDomainServiceImpl< CustomerOption, Long> implements CustomerOptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionServiceImpl.class);
    private final CustomerOptionRepository customerOptionRepository;
    private final CustomerOptionSearchRepository customerOptionSearchRepository;
    
    public CustomerOptionServiceImpl(CustomerOptionRepository customerOptionRepository, CustomerOptionSearchRepository customerOptionSearchRepository) {
        super(customerOptionRepository,customerOptionSearchRepository);
        this.customerOptionRepository = customerOptionRepository;
        this.customerOptionSearchRepository = customerOptionSearchRepository;
    }
    
}
