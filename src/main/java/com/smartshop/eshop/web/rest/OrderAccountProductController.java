package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderAccountProduct;
import com.smartshop.eshop.service.OrderAccountProductService;

/**
 * REST controller for managing OrderAccountProduct.
 */
@RestController
@RequestMapping("/api/"+ OrderAccountProductController.SECTION_KEY)
public class OrderAccountProductController extends AbstractDomainController< OrderAccountProduct, Long>{

    private final Logger log = LoggerFactory.getLogger(OrderAccountProductController.class);
    public static final String SECTION_KEY = "order-account-products";
    private static final String ENTITY_NAME = "orderAccountProduct";
        
     private final OrderAccountProductService orderAccountProductService;

    public OrderAccountProductController(OrderAccountProductService orderAccountProductService) {
        super(orderAccountProductService);
        this.orderAccountProductService = orderAccountProductService;
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