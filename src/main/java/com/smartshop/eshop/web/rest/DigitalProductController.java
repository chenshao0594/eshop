package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.DigitalProduct;
import com.smartshop.eshop.service.DigitalProductService;

/**
 * REST controller for managing DigitalProduct.
 */
@RestController
@RequestMapping("/api/"+ DigitalProductController.SECTION_KEY)
public class DigitalProductController extends AbstractDomainController< DigitalProduct, Long>{

    private final Logger log = LoggerFactory.getLogger(DigitalProductController.class);
    public static final String SECTION_KEY = "digital-products";
    private static final String ENTITY_NAME = "digitalProduct";
        
     private final DigitalProductService digitalProductService;

    public DigitalProductController(DigitalProductService digitalProductService) {
        super(digitalProductService);
        this.digitalProductService = digitalProductService;
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