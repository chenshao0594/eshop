package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerOptionValueDescriptionService;
import com.smartshop.eshop.domain.CustomerOptionValueDescription;
import com.smartshop.eshop.repository.CustomerOptionValueDescriptionRepository;
import com.smartshop.eshop.repository.search.CustomerOptionValueDescriptionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CustomerOptionValueDescription.
 */
@Service
@Transactional
public class CustomerOptionValueDescriptionServiceImpl extends AbstractDomainServiceImpl< CustomerOptionValueDescription, Long> implements CustomerOptionValueDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionValueDescriptionServiceImpl.class);
    private final CustomerOptionValueDescriptionRepository customerOptionValueDescriptionRepository;
    private final CustomerOptionValueDescriptionSearchRepository customerOptionValueDescriptionSearchRepository;
    
    public CustomerOptionValueDescriptionServiceImpl(CustomerOptionValueDescriptionRepository customerOptionValueDescriptionRepository, CustomerOptionValueDescriptionSearchRepository customerOptionValueDescriptionSearchRepository) {
        super(customerOptionValueDescriptionRepository,customerOptionValueDescriptionSearchRepository);
        this.customerOptionValueDescriptionRepository = customerOptionValueDescriptionRepository;
        this.customerOptionValueDescriptionSearchRepository = customerOptionValueDescriptionSearchRepository;
    }
    
}
