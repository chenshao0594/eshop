package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ShoppingCartService;
import com.smartshop.eshop.domain.ShoppingCart;
import com.smartshop.eshop.repository.ShoppingCartRepository;
import com.smartshop.eshop.repository.search.ShoppingCartSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
