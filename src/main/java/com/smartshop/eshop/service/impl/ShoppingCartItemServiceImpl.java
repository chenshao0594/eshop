package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.repository.ShoppingCartItemRepository;
import com.smartshop.eshop.repository.search.ShoppingCartItemSearchRepository;
import com.smartshop.eshop.service.ShoppingCartItemService;

/**
 * Service Implementation for managing ShoppingCartItem.
 */
@Service
@Transactional
public class ShoppingCartItemServiceImpl extends AbstractDomainServiceImpl< ShoppingCartItem, Long> implements ShoppingCartItemService{

    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartItemServiceImpl.class);
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final ShoppingCartItemSearchRepository shoppingCartItemSearchRepository;
    
    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository, ShoppingCartItemSearchRepository shoppingCartItemSearchRepository) {
        super(shoppingCartItemRepository,shoppingCartItemSearchRepository);
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.shoppingCartItemSearchRepository = shoppingCartItemSearchRepository;
    }
    
}
