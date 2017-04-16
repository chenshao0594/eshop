package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductReviewDescription;
import com.smartshop.eshop.repository.ProductReviewDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductReviewDescriptionSearchRepository;
import com.smartshop.eshop.service.ProductReviewDescriptionService;

/**
 * Service Implementation for managing ProductReviewDescription.
 */
@Service
@Transactional
public class ProductReviewDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductReviewDescription, Long> implements ProductReviewDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductReviewDescriptionServiceImpl.class);
    private final ProductReviewDescriptionRepository productReviewDescriptionRepository;
    private final ProductReviewDescriptionSearchRepository productReviewDescriptionSearchRepository;
    
    public ProductReviewDescriptionServiceImpl(ProductReviewDescriptionRepository productReviewDescriptionRepository, ProductReviewDescriptionSearchRepository productReviewDescriptionSearchRepository) {
        super(productReviewDescriptionRepository,productReviewDescriptionSearchRepository);
        this.productReviewDescriptionRepository = productReviewDescriptionRepository;
        this.productReviewDescriptionSearchRepository = productReviewDescriptionSearchRepository;
    }
    
}
