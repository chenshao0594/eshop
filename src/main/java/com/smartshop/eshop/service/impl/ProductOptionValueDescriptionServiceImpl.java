package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductOptionValueDescriptionService;
import com.smartshop.eshop.domain.ProductOptionValueDescription;
import com.smartshop.eshop.repository.ProductOptionValueDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductOptionValueDescriptionSearchRepository;
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
 * Service Implementation for managing ProductOptionValueDescription.
 */
@Service
@Transactional
public class ProductOptionValueDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductOptionValueDescription, Long> implements ProductOptionValueDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionValueDescriptionServiceImpl.class);
    private final ProductOptionValueDescriptionRepository productOptionValueDescriptionRepository;
    private final ProductOptionValueDescriptionSearchRepository productOptionValueDescriptionSearchRepository;
    
    public ProductOptionValueDescriptionServiceImpl(ProductOptionValueDescriptionRepository productOptionValueDescriptionRepository, ProductOptionValueDescriptionSearchRepository productOptionValueDescriptionSearchRepository) {
        super(productOptionValueDescriptionRepository,productOptionValueDescriptionSearchRepository);
        this.productOptionValueDescriptionRepository = productOptionValueDescriptionRepository;
        this.productOptionValueDescriptionSearchRepository = productOptionValueDescriptionSearchRepository;
    }
    
}
