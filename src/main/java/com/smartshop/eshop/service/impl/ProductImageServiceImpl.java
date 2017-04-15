package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ProductImageService;
import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.repository.ProductImageRepository;
import com.smartshop.eshop.repository.search.ProductImageSearchRepository;
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
 * Service Implementation for managing ProductImage.
 */
@Service
@Transactional
public class ProductImageServiceImpl extends AbstractDomainServiceImpl< ProductImage, Long> implements ProductImageService{

    private final Logger LOGGER = LoggerFactory.getLogger(ProductImageServiceImpl.class);
    private final ProductImageRepository productImageRepository;
    private final ProductImageSearchRepository productImageSearchRepository;
    
    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductImageSearchRepository productImageSearchRepository) {
        super(productImageRepository,productImageSearchRepository);
        this.productImageRepository = productImageRepository;
        this.productImageSearchRepository = productImageSearchRepository;
    }
    
}
