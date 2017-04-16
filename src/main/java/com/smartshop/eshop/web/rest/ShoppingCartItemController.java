package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.service.ShoppingCartItemService;

/**
 * REST controller for managing ShoppingCartItem.
 */
@RestController
@RequestMapping("/api/"+ ShoppingCartItemController.SECTION_KEY)
public class ShoppingCartItemController extends AbstractDomainController< ShoppingCartItem, Long>{

    private final Logger log = LoggerFactory.getLogger(ShoppingCartItemController.class);
    public static final String SECTION_KEY = "shopping-cart-items";
    private static final String ENTITY_NAME = "shoppingCartItem";
        
     private final ShoppingCartItemService shoppingCartItemService;

    public ShoppingCartItemController(ShoppingCartItemService shoppingCartItemService) {
        super(shoppingCartItemService);
        this.shoppingCartItemService = shoppingCartItemService;
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