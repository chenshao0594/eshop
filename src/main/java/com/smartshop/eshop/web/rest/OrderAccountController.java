package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderAccount;
import com.smartshop.eshop.service.OrderAccountService;

/**
 * REST controller for managing OrderAccount.
 */
@RestController
@RequestMapping("/api/"+ OrderAccountController.SECTION_KEY)
public class OrderAccountController extends AbstractDomainController< OrderAccount, Long>{

    private final Logger log = LoggerFactory.getLogger(OrderAccountController.class);
    public static final String SECTION_KEY = "order-accounts";
    private static final String ENTITY_NAME = "orderAccount";
        
     private final OrderAccountService orderAccountService;

    public OrderAccountController(OrderAccountService orderAccountService) {
        super(orderAccountService);
        this.orderAccountService = orderAccountService;
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