package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.service.OrderTotalService;

/**
 * REST controller for managing OrderTotal.
 */
@RestController
@RequestMapping("/api/"+ OrderTotalController.SECTION_KEY)
public class OrderTotalController extends AbstractDomainController< OrderTotal, Long>{

    private final Logger log = LoggerFactory.getLogger(OrderTotalController.class);
    public static final String SECTION_KEY = "order-totals";
    private static final String ENTITY_NAME = "orderTotal";
        
     private final OrderTotalService orderTotalService;

    public OrderTotalController(OrderTotalService orderTotalService) {
        super(orderTotalService);
        this.orderTotalService = orderTotalService;
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