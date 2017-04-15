package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductOptionDescriptionService;
import com.smartshop.eshop.domain.ProductOptionDescription;
import com.smartshop.eshop.repository.ProductOptionDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductOptionDescriptionSearchRepository;
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
 * Service Implementation for managing ProductOptionDescription.
 */
@Service
@Transactional
public class ProductOptionDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductOptionDescription, Long> implements ProductOptionDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionDescriptionServiceImpl.class);
    private final ProductOptionDescriptionRepository productOptionDescriptionRepository;
    private final ProductOptionDescriptionSearchRepository productOptionDescriptionSearchRepository;
    
    public ProductOptionDescriptionServiceImpl(ProductOptionDescriptionRepository productOptionDescriptionRepository, ProductOptionDescriptionSearchRepository productOptionDescriptionSearchRepository) {
        super(productOptionDescriptionRepository,productOptionDescriptionSearchRepository);
        this.productOptionDescriptionRepository = productOptionDescriptionRepository;
        this.productOptionDescriptionSearchRepository = productOptionDescriptionSearchRepository;
    }
    
}
