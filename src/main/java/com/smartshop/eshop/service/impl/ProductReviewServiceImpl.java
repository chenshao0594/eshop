package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductReviewService;
import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.repository.ProductReviewRepository;
import com.smartshop.eshop.repository.search.ProductReviewSearchRepository;
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
