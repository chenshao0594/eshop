package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CustomerAttribute;
import com.smartshop.eshop.repository.CustomerAttributeRepository;
import com.smartshop.eshop.repository.search.CustomerAttributeSearchRepository;
import com.smartshop.eshop.service.CustomerAttributeService;

/**
 * Service Implementation for managing CustomerAttribute.
 */
@Service
@Transactional
public class CustomerAttributeServiceImpl extends AbstractDomainServiceImpl< CustomerAttribute, Long> implements CustomerAttributeService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerAttributeServiceImpl.class);
    private final CustomerAttributeRepository customerAttributeRepository;
    private final CustomerAttributeSearchRepository customerAttributeSearchRepository;
    
    public CustomerAttributeServiceImpl(CustomerAttributeRepository customerAttributeRepository, CustomerAttributeSearchRepository customerAttributeSearchRepository) {
        super(customerAttributeRepository,customerAttributeSearchRepository);
        this.customerAttributeRepository = customerAttributeRepository;
        this.customerAttributeSearchRepository = customerAttributeSearchRepository;
    }
    
}
