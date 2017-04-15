package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.DigitalProductService;
import com.smartshop.eshop.domain.DigitalProduct;
import com.smartshop.eshop.repository.DigitalProductRepository;
import com.smartshop.eshop.repository.search.DigitalProductSearchRepository;
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
 * Service Implementation for managing DigitalProduct.
 */
@Service
@Transactional
public class DigitalProductServiceImpl extends AbstractDomainServiceImpl< DigitalProduct, Long> implements DigitalProductService{

    private final Logger LOGGER = LoggerFactory.getLogger(DigitalProductServiceImpl.class);
    private final DigitalProductRepository digitalProductRepository;
    private final DigitalProductSearchRepository digitalProductSearchRepository;
    
    public DigitalProductServiceImpl(DigitalProductRepository digitalProductRepository, DigitalProductSearchRepository digitalProductSearchRepository) {
        super(digitalProductRepository,digitalProductSearchRepository);
        this.digitalProductRepository = digitalProductRepository;
        this.digitalProductSearchRepository = digitalProductSearchRepository;
    }
    
}
