package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerOptionSetService;
import com.smartshop.eshop.domain.CustomerOptionSet;
import com.smartshop.eshop.repository.CustomerOptionSetRepository;
import com.smartshop.eshop.repository.search.CustomerOptionSetSearchRepository;
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
 * Service Implementation for managing CustomerOptionSet.
 */
@Service
@Transactional
public class CustomerOptionSetServiceImpl extends AbstractDomainServiceImpl< CustomerOptionSet, Long> implements CustomerOptionSetService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionSetServiceImpl.class);
    private final CustomerOptionSetRepository customerOptionSetRepository;
    private final CustomerOptionSetSearchRepository customerOptionSetSearchRepository;
    
    public CustomerOptionSetServiceImpl(CustomerOptionSetRepository customerOptionSetRepository, CustomerOptionSetSearchRepository customerOptionSetSearchRepository) {
        super(customerOptionSetRepository,customerOptionSetSearchRepository);
        this.customerOptionSetRepository = customerOptionSetRepository;
        this.customerOptionSetSearchRepository = customerOptionSetSearchRepository;
    }
    
}
