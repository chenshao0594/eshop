package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ShoppingCartItemService;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.repository.ShoppingCartItemRepository;
import com.smartshop.eshop.repository.search.ShoppingCartItemSearchRepository;
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
