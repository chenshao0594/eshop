package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductService;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.repository.ProductRepository;
import com.smartshop.eshop.repository.search.ProductSearchRepository;
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
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractDomainServiceImpl< Product, Long> implements ProductService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    
    public ProductServiceImpl(ProductRepository productRepository, ProductSearchRepository productSearchRepository) {
        super(productRepository,productSearchRepository);
        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
    }
    
}
