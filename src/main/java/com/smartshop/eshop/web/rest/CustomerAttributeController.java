package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CustomerAttribute;
import com.smartshop.eshop.service.CustomerAttributeService;

/**
 * REST controller for managing CustomerAttribute.
 */
@RestController
@RequestMapping("/api/"+ CustomerAttributeController.SECTION_KEY)
public class CustomerAttributeController extends AbstractDomainController< CustomerAttribute, Long>{

    private final Logger log = LoggerFactory.getLogger(CustomerAttributeController.class);
    public static final String SECTION_KEY = "customer-attributes";
    private static final String ENTITY_NAME = "customerAttribute";
        
     private final CustomerAttributeService customerAttributeService;

    public CustomerAttributeController(CustomerAttributeService customerAttributeService) {
        super(customerAttributeService);
        this.customerAttributeService = customerAttributeService;
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