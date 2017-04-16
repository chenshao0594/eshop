package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.repository.ShoppingCartRepository;
import com.smartshop.eshop.repository.search.ShoppingCartSearchRepository;
import com.smartshop.eshop.service.ShoppingCartService;

/**
 * Service Implementation for managing ShoppingCart.
 */
@Service
@Transactional
public class ShoppingCartServiceImpl extends AbstractDomainServiceImpl< ShoppingCart, Long> implements ShoppingCartService{

    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartSearchRepository shoppingCartSearchRepository;
    
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartSearchRepository shoppingCartSearchRepository) {
        super(shoppingCartRepository,shoppingCartSearchRepository);
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartSearchRepository = shoppingCartSearchRepository;
    }
    
}
