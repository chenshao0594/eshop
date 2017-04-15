package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductAvailabilityService;
import com.smartshop.eshop.domain.ProductAvailability;
import com.smartshop.eshop.repository.ProductAvailabilityRepository;
import com.smartshop.eshop.repository.search.ProductAvailabilitySearchRepository;
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
 * Service Implementation for managing ProductAvailability.
 */
@Service
@Transactional
public class ProductAvailabilityServiceImpl extends AbstractDomainServiceImpl< ProductAvailability, Long> implements ProductAvailabilityService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductAvailabilityServiceImpl.class);
    private final ProductAvailabilityRepository productAvailabilityRepository;
    private final ProductAvailabilitySearchRepository productAvailabilitySearchRepository;
    
    public ProductAvailabilityServiceImpl(ProductAvailabilityRepository productAvailabilityRepository, ProductAvailabilitySearchRepository productAvailabilitySearchRepository) {
        super(productAvailabilityRepository,productAvailabilitySearchRepository);
        this.productAvailabilityRepository = productAvailabilityRepository;
        this.productAvailabilitySearchRepository = productAvailabilitySearchRepository;
    }
    
}
