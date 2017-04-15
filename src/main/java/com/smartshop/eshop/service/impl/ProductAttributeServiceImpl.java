package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductAttributeService;
import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.repository.ProductAttributeRepository;
import com.smartshop.eshop.repository.search.ProductAttributeSearchRepository;
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
 * Service Implementation for managing ProductAttribute.
 */
@Service
@Transactional
public class ProductAttributeServiceImpl extends AbstractDomainServiceImpl< ProductAttribute, Long> implements ProductAttributeService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);
    private final ProductAttributeRepository productAttributeRepository;
    private final ProductAttributeSearchRepository productAttributeSearchRepository;
    
    public ProductAttributeServiceImpl(ProductAttributeRepository productAttributeRepository, ProductAttributeSearchRepository productAttributeSearchRepository) {
        super(productAttributeRepository,productAttributeSearchRepository);
        this.productAttributeRepository = productAttributeRepository;
        this.productAttributeSearchRepository = productAttributeSearchRepository;
    }
    
}
