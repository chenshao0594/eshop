package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductPriceService;
import com.smartshop.eshop.domain.ProductPrice;
import com.smartshop.eshop.repository.ProductPriceRepository;
import com.smartshop.eshop.repository.search.ProductPriceSearchRepository;
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
 * Service Implementation for managing ProductPrice.
 */
@Service
@Transactional
public class ProductPriceServiceImpl extends AbstractDomainServiceImpl< ProductPrice, Long> implements ProductPriceService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductPriceServiceImpl.class);
    private final ProductPriceRepository productPriceRepository;
    private final ProductPriceSearchRepository productPriceSearchRepository;
    
    public ProductPriceServiceImpl(ProductPriceRepository productPriceRepository, ProductPriceSearchRepository productPriceSearchRepository) {
        super(productPriceRepository,productPriceSearchRepository);
        this.productPriceRepository = productPriceRepository;
        this.productPriceSearchRepository = productPriceSearchRepository;
    }
    
}
