package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.repository.ProductDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductDescriptionSearchRepository;
import com.smartshop.eshop.service.ProductDescriptionService;

/**
 * Service Implementation for managing ProductDescription.
 */
@Service
@Transactional
public class ProductDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductDescription, Long> implements ProductDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductDescriptionServiceImpl.class);
    private final ProductDescriptionRepository productDescriptionRepository;
    private final ProductDescriptionSearchRepository productDescriptionSearchRepository;
    
    public ProductDescriptionServiceImpl(ProductDescriptionRepository productDescriptionRepository, ProductDescriptionSearchRepository productDescriptionSearchRepository) {
        super(productDescriptionRepository,productDescriptionSearchRepository);
        this.productDescriptionRepository = productDescriptionRepository;
        this.productDescriptionSearchRepository = productDescriptionSearchRepository;
    }
    
}
