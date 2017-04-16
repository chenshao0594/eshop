package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderProduct;
import com.smartshop.eshop.service.OrderProductService;

/**
 * REST controller for managing OrderProduct.
 */
@RestController
@RequestMapping("/api/"+ OrderProductController.SECTION_KEY)
public class OrderProductController extends AbstractDomainController< OrderProduct, Long>{

    private final Logger log = LoggerFactory.getLogger(OrderProductController.class);
    public static final String SECTION_KEY = "order-products";
    private static final String ENTITY_NAME = "orderProduct";
        
     private final OrderProductService orderProductService;

    public OrderProductController(OrderProductService orderProductService) {
        super(orderProductService);
        this.orderProductService = orderProductService;
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