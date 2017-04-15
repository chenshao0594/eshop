package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerOptinService;
import com.smartshop.eshop.domain.CustomerOptin;
import com.smartshop.eshop.repository.CustomerOptinRepository;
import com.smartshop.eshop.repository.search.CustomerOptinSearchRepository;
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
 * Service Implementation for managing CustomerOptin.
 */
@Service
@Transactional
public class CustomerOptinServiceImpl extends AbstractDomainServiceImpl< CustomerOptin, Long> implements CustomerOptinService{

    private final Logger LOGGER = LoggerFactory.getLogger(CustomerOptinServiceImpl.class);
    private final CustomerOptinRepository customerOptinRepository;
    private final CustomerOptinSearchRepository customerOptinSearchRepository;
    
    public CustomerOptinServiceImpl(CustomerOptinRepository customerOptinRepository, CustomerOptinSearchRepository customerOptinSearchRepository) {
        super(customerOptinRepository,customerOptinSearchRepository);
        this.customerOptinRepository = customerOptinRepository;
        this.customerOptinSearchRepository = customerOptinSearchRepository;
    }
    
}
