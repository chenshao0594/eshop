package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.service.ShoppingCartService;

/**
 * REST controller for managing ShoppingCart.
 */
@RestController
@RequestMapping("/api/"+ ShoppingCartController.SECTION_KEY)
public class ShoppingCartController extends AbstractDomainController< ShoppingCart, Long>{

    private final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
    public static final String SECTION_KEY = "shopping-carts";
    private static final String ENTITY_NAME = "shoppingCart";
        
     private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        super(shoppingCartService);
        this.shoppingCartService = shoppingCartService;
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