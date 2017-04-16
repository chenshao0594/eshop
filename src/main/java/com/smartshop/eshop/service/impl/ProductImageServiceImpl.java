package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.repository.ProductImageRepository;
import com.smartshop.eshop.repository.search.ProductImageSearchRepository;
import com.smartshop.eshop.service.ProductImageService;

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
