package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductReviewDescriptionService;
import com.smartshop.eshop.domain.ProductReviewDescription;
import com.smartshop.eshop.repository.ProductReviewDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductReviewDescriptionSearchRepository;
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
