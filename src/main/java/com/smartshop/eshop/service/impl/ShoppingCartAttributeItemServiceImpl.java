package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ShoppingCartAttributeItemService;
import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import com.smartshop.eshop.repository.ShoppingCartAttributeItemRepository;
import com.smartshop.eshop.repository.search.ShoppingCartAttributeItemSearchRepository;
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
 * Service Implementation for managing ShoppingCartAttributeItem.
 */
@Service
@Transactional
public class ShoppingCartAttributeItemServiceImpl extends AbstractDomainServiceImpl< ShoppingCartAttributeItem, Long> implements ShoppingCartAttributeItemService{

    private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartAttributeItemServiceImpl.class);
    private final ShoppingCartAttributeItemRepository shoppingCartAttributeItemRepository;
    private final ShoppingCartAttributeItemSearchRepository shoppingCartAttributeItemSearchRepository;
    
    public ShoppingCartAttributeItemServiceImpl(ShoppingCartAttributeItemRepository shoppingCartAttributeItemRepository, ShoppingCartAttributeItemSearchRepository shoppingCartAttributeItemSearchRepository) {
        super(shoppingCartAttributeItemRepository,shoppingCartAttributeItemSearchRepository);
        this.shoppingCartAttributeItemRepository = shoppingCartAttributeItemRepository;
        this.shoppingCartAttributeItemSearchRepository = shoppingCartAttributeItemSearchRepository;
    }
    
}
