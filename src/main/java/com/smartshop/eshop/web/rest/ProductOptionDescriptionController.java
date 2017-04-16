package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductOptionDescription;
import com.smartshop.eshop.service.ProductOptionDescriptionService;

/**
 * REST controller for managing ProductOptionDescription.
 */
@RestController
@RequestMapping("/api/"+ ProductOptionDescriptionController.SECTION_KEY)
public class ProductOptionDescriptionController extends AbstractDomainController< ProductOptionDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductOptionDescriptionController.class);
    public static final String SECTION_KEY = "product-option-descriptions";
    private static final String ENTITY_NAME = "productOptionDescription";
        
     private final ProductOptionDescriptionService productOptionDescriptionService;

    public ProductOptionDescriptionController(ProductOptionDescriptionService productOptionDescriptionService) {
        super(productOptionDescriptionService);
        this.productOptionDescriptionService = productOptionDescriptionService;
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