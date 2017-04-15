package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductDescriptionService;
import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.repository.ProductDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductDescriptionSearchRepository;
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
