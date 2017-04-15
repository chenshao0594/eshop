package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductImageDescriptionService;
import com.smartshop.eshop.domain.ProductImageDescription;
import com.smartshop.eshop.repository.ProductImageDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductImageDescriptionSearchRepository;
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
 * Service Implementation for managing ProductImageDescription.
 */
@Service
@Transactional
public class ProductImageDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductImageDescription, Long> implements ProductImageDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductImageDescriptionServiceImpl.class);
    private final ProductImageDescriptionRepository productImageDescriptionRepository;
    private final ProductImageDescriptionSearchRepository productImageDescriptionSearchRepository;
    
    public ProductImageDescriptionServiceImpl(ProductImageDescriptionRepository productImageDescriptionRepository, ProductImageDescriptionSearchRepository productImageDescriptionSearchRepository) {
        super(productImageDescriptionRepository,productImageDescriptionSearchRepository);
        this.productImageDescriptionRepository = productImageDescriptionRepository;
        this.productImageDescriptionSearchRepository = productImageDescriptionSearchRepository;
    }
    
}
