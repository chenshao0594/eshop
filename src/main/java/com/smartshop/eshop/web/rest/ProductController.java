package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.service.ProductService;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api/"+ ProductController.SECTION_KEY)
public class ProductController extends AbstractDomainController< Product, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductController.class);
    public static final String SECTION_KEY = "products";
    private static final String ENTITY_NAME = "product";
        
     private final ProductService productService;

    public ProductController(ProductService productService) {
        super(productService);
        this.productService = productService;
    }    
    @Override
    protected String getSectionKey() {
        return SECTION_KEY;
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

}