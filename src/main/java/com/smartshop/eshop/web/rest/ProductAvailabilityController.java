package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductAvailability;
import com.smartshop.eshop.service.ProductAvailabilityService;

/**
 * REST controller for managing ProductAvailability.
 */
@RestController
@RequestMapping("/api/"+ ProductAvailabilityController.SECTION_KEY)
public class ProductAvailabilityController extends AbstractDomainController< ProductAvailability, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductAvailabilityController.class);
    public static final String SECTION_KEY = "product-availabilities";
    private static final String ENTITY_NAME = "productAvailability";
        
     private final ProductAvailabilityService productAvailabilityService;

    public ProductAvailabilityController(ProductAvailabilityService productAvailabilityService) {
        super(productAvailabilityService);
        this.productAvailabilityService = productAvailabilityService;
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