package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderProductAttribute;
import com.smartshop.eshop.service.OrderProductAttributeService;

/**
 * REST controller for managing OrderProductAttribute.
 */
@RestController
@RequestMapping("/api/"+ OrderProductAttributeController.SECTION_KEY)
public class OrderProductAttributeController extends AbstractDomainController< OrderProductAttribute, Long>{

    private final Logger log = LoggerFactory.getLogger(OrderProductAttributeController.class);
    public static final String SECTION_KEY = "order-product-attributes";
    private static final String ENTITY_NAME = "orderProductAttribute";
        
     private final OrderProductAttributeService orderProductAttributeService;

    public OrderProductAttributeController(OrderProductAttributeService orderProductAttributeService) {
        super(orderProductAttributeService);
        this.orderProductAttributeService = orderProductAttributeService;
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