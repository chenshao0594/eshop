package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductOptionValue;
import com.smartshop.eshop.service.ProductOptionValueService;

/**
 * REST controller for managing ProductOptionValue.
 */
@RestController
@RequestMapping("/api/"+ ProductOptionValueController.SECTION_KEY)
public class ProductOptionValueController extends AbstractDomainController< ProductOptionValue, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductOptionValueController.class);
    public static final String SECTION_KEY = "product-option-values";
    private static final String ENTITY_NAME = "productOptionValue";
        
     private final ProductOptionValueService productOptionValueService;

    public ProductOptionValueController(ProductOptionValueService productOptionValueService) {
        super(productOptionValueService);
        this.productOptionValueService = productOptionValueService;
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