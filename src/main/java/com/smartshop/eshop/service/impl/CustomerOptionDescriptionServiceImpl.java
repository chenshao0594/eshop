package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerOptionDescriptionService;
import com.smartshop.eshop.domain.CustomerOptionDescription;
import com.smartshop.eshop.repository.CustomerOptionDescriptionRepository;
import com.smartshop.eshop.repository.search.CustomerOptionDescriptionSearchRepository;
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
