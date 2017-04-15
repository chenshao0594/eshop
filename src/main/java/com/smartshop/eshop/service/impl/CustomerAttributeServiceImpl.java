package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CustomerAttributeService;
import com.smartshop.eshop.domain.CustomerAttribute;
import com.smartshop.eshop.repository.CustomerAttributeRepository;
import com.smartshop.eshop.repository.search.CustomerAttributeSearchRepository;
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
