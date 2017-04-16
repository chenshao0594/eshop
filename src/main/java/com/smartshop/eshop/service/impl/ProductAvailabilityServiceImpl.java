package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductAvailability;
import com.smartshop.eshop.repository.ProductAvailabilityRepository;
import com.smartshop.eshop.repository.search.ProductAvailabilitySearchRepository;
import com.smartshop.eshop.service.ProductAvailabilityService;

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
