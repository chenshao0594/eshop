package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductTypeService;
import com.smartshop.eshop.domain.ProductType;
import com.smartshop.eshop.repository.ProductTypeRepository;
import com.smartshop.eshop.repository.search.ProductTypeSearchRepository;
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
 * Service Implementation for managing ProductType.
 */
@Service
@Transactional
public class ProductTypeServiceImpl extends AbstractDomainServiceImpl< ProductType, Long> implements ProductTypeService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductTypeServiceImpl.class);
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeSearchRepository productTypeSearchRepository;
    
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ProductTypeSearchRepository productTypeSearchRepository) {
        super(productTypeRepository,productTypeSearchRepository);
        this.productTypeRepository = productTypeRepository;
        this.productTypeSearchRepository = productTypeSearchRepository;
    }
    
}
