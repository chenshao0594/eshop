package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerOptionValueService;
import com.smartshop.eshop.domain.CustomerOptionValue;
import com.smartshop.eshop.repository.CustomerOptionValueRepository;
import com.smartshop.eshop.repository.search.CustomerOptionValueSearchRepository;
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
 * Service Implementation for managing CustomerOptionValue.
 */
@Service
@Transactional
public class CustomerOptionValueServiceImpl extends AbstractDomainServiceImpl< CustomerOptionValue, Long> implements CustomerOptionValueService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptionValueServiceImpl.class);
    private final CustomerOptionValueRepository customerOptionValueRepository;
    private final CustomerOptionValueSearchRepository customerOptionValueSearchRepository;
    
    public CustomerOptionValueServiceImpl(CustomerOptionValueRepository customerOptionValueRepository, CustomerOptionValueSearchRepository customerOptionValueSearchRepository) {
        super(customerOptionValueRepository,customerOptionValueSearchRepository);
        this.customerOptionValueRepository = customerOptionValueRepository;
        this.customerOptionValueSearchRepository = customerOptionValueSearchRepository;
    }
    
}
