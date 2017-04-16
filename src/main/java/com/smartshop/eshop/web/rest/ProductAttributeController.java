package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.service.ProductAttributeService;

/**
 * REST controller for managing ProductAttribute.
 */
@RestController
@RequestMapping("/api/"+ ProductAttributeController.SECTION_KEY)
public class ProductAttributeController extends AbstractDomainController< ProductAttribute, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductAttributeController.class);
    public static final String SECTION_KEY = "product-attributes";
    private static final String ENTITY_NAME = "productAttribute";
        
     private final ProductAttributeService productAttributeService;

    public ProductAttributeController(ProductAttributeService productAttributeService) {
        super(productAttributeService);
        this.productAttributeService = productAttributeService;
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