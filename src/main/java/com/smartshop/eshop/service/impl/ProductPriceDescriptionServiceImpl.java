package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductPriceDescriptionService;
import com.smartshop.eshop.domain.ProductPriceDescription;
import com.smartshop.eshop.repository.ProductPriceDescriptionRepository;
import com.smartshop.eshop.repository.search.ProductPriceDescriptionSearchRepository;
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
 * Service Implementation for managing ProductPriceDescription.
 */
@Service
@Transactional
public class ProductPriceDescriptionServiceImpl extends AbstractDomainServiceImpl< ProductPriceDescription, Long> implements ProductPriceDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductPriceDescriptionServiceImpl.class);
    private final ProductPriceDescriptionRepository productPriceDescriptionRepository;
    private final ProductPriceDescriptionSearchRepository productPriceDescriptionSearchRepository;
    
    public ProductPriceDescriptionServiceImpl(ProductPriceDescriptionRepository productPriceDescriptionRepository, ProductPriceDescriptionSearchRepository productPriceDescriptionSearchRepository) {
        super(productPriceDescriptionRepository,productPriceDescriptionSearchRepository);
        this.productPriceDescriptionRepository = productPriceDescriptionRepository;
        this.productPriceDescriptionSearchRepository = productPriceDescriptionSearchRepository;
    }
    
}
