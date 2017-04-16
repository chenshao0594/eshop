package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.repository.ProductRelationshipRepository;
import com.smartshop.eshop.repository.search.ProductRelationshipSearchRepository;
import com.smartshop.eshop.service.ProductRelationshipService;

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
