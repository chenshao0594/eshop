package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOptionSet;
import com.smartshop.eshop.service.CustomerOptionSetService;

/**
 * REST controller for managing CustomerOptionSet.
 */
@RestController
@RequestMapping("/api/"+ CustomerOptionSetController.SECTION_KEY)
public class CustomerOptionSetController extends AbstractDomainController< CustomerOptionSet, Long>{

    private final Logger log = LoggerFactory.getLogger(CustomerOptionSetController.class);
    public static final String SECTION_KEY = "customer-option-sets";
    private static final String ENTITY_NAME = "customerOptionSet";
        
     private final CustomerOptionSetService customerOptionSetService;

    public CustomerOptionSetController(CustomerOptionSetService customerOptionSetService) {
        super(customerOptionSetService);
        this.customerOptionSetService = customerOptionSetService;
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