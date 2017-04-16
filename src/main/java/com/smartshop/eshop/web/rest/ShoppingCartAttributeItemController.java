package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import com.smartshop.eshop.service.ShoppingCartAttributeItemService;

/**
 * REST controller for managing ShoppingCartAttributeItem.
 */
@RestController
@RequestMapping("/api/"+ ShoppingCartAttributeItemController.SECTION_KEY)
public class ShoppingCartAttributeItemController extends AbstractDomainController< ShoppingCartAttributeItem, Long>{

    private final Logger log = LoggerFactory.getLogger(ShoppingCartAttributeItemController.class);
    public static final String SECTION_KEY = "shopping-cart-attribute-items";
    private static final String ENTITY_NAME = "shoppingCartAttributeItem";
        
     private final ShoppingCartAttributeItemService shoppingCartAttributeItemService;

    public ShoppingCartAttributeItemController(ShoppingCartAttributeItemService shoppingCartAttributeItemService) {
        super(shoppingCartAttributeItemService);
        this.shoppingCartAttributeItemService = shoppingCartAttributeItemService;
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