package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductOptionService;
import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.repository.ProductOptionRepository;
import com.smartshop.eshop.repository.search.ProductOptionSearchRepository;
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
 * Service Implementation for managing ProductOption.
 */
@Service
@Transactional
public class ProductOptionServiceImpl extends AbstractDomainServiceImpl< ProductOption, Long> implements ProductOptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductOptionServiceImpl.class);
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionSearchRepository productOptionSearchRepository;
    
    public ProductOptionServiceImpl(ProductOptionRepository productOptionRepository, ProductOptionSearchRepository productOptionSearchRepository) {
        super(productOptionRepository,productOptionSearchRepository);
        this.productOptionRepository = productOptionRepository;
        this.productOptionSearchRepository = productOptionSearchRepository;
    }
    
}
