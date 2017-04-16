package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.service.CustomerService;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api/"+ CustomerController.SECTION_KEY)
public class CustomerController extends AbstractDomainController< Customer, Long>{

    private final Logger log = LoggerFactory.getLogger(CustomerController.class);
    public static final String SECTION_KEY = "customers";
    private static final String ENTITY_NAME = "customer";
        
     private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        super(customerService);
        this.customerService = customerService;
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