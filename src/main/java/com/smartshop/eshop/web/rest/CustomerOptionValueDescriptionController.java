package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;
import com.smartshop.eshop.service.CustomerOptionValueDescriptionService;

/**
 * REST controller for managing CustomerOptionValueDescription.
 */
@RestController
@RequestMapping("/api/"+ CustomerOptionValueDescriptionController.SECTION_KEY)
public class CustomerOptionValueDescriptionController extends AbstractDomainController< CustomerOptionValueDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(CustomerOptionValueDescriptionController.class);
    public static final String SECTION_KEY = "customer-option-value-descriptions";
    private static final String ENTITY_NAME = "customerOptionValueDescription";
        
     private final CustomerOptionValueDescriptionService customerOptionValueDescriptionService;

    public CustomerOptionValueDescriptionController(CustomerOptionValueDescriptionService customerOptionValueDescriptionService) {
        super(customerOptionValueDescriptionService);
        this.customerOptionValueDescriptionService = customerOptionValueDescriptionService;
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