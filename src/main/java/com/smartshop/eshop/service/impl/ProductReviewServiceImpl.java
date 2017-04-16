package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.repository.ProductReviewRepository;
import com.smartshop.eshop.repository.search.ProductReviewSearchRepository;
import com.smartshop.eshop.service.ProductReviewService;

/**
 * Service Implementation for managing ProductReview.
 */
@Service
@Transactional
public class ProductReviewServiceImpl extends AbstractDomainServiceImpl< ProductReview, Long> implements ProductReviewService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductReviewServiceImpl.class);
    private final ProductReviewRepository productReviewRepository;
    private final ProductReviewSearchRepository productReviewSearchRepository;
    
    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository, ProductReviewSearchRepository productReviewSearchRepository) {
        super(productReviewRepository,productReviewSearchRepository);
        this.productReviewRepository = productReviewRepository;
        this.productReviewSearchRepository = productReviewSearchRepository;
    }
    
}
