package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductRelationshipService;
import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.repository.ProductRelationshipRepository;
import com.smartshop.eshop.repository.search.ProductRelationshipSearchRepository;
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
 * Service Implementation for managing ProductRelationship.
 */
@Service
@Transactional
public class ProductRelationshipServiceImpl extends AbstractDomainServiceImpl< ProductRelationship, Long> implements ProductRelationshipService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductRelationshipServiceImpl.class);
    private final ProductRelationshipRepository productRelationshipRepository;
    private final ProductRelationshipSearchRepository productRelationshipSearchRepository;
    
    public ProductRelationshipServiceImpl(ProductRelationshipRepository productRelationshipRepository, ProductRelationshipSearchRepository productRelationshipSearchRepository) {
        super(productRelationshipRepository,productRelationshipSearchRepository);
        this.productRelationshipRepository = productRelationshipRepository;
        this.productRelationshipSearchRepository = productRelationshipSearchRepository;
    }
    
}
