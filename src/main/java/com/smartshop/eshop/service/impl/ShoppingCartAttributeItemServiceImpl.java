package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import com.smartshop.eshop.repository.ShoppingCartAttributeItemRepository;
import com.smartshop.eshop.repository.search.ShoppingCartAttributeItemSearchRepository;
import com.smartshop.eshop.service.ShoppingCartAttributeItemService;

/**
 * Service Implementation for managing ShoppingCartAttributeItem.
 */
@Service
@Transactional
public class ShoppingCartAttributeItemServiceImpl extends AbstractDomainServiceImpl<ShoppingCartAttributeItem, Long>
		implements ShoppingCartAttributeItemService {

	private final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartAttributeItemServiceImpl.class);
	private final ShoppingCartAttributeItemRepository shoppingCartAttributeItemRepository;
	private final ShoppingCartAttributeItemSearchRepository shoppingCartAttributeItemSearchRepository;

	public ShoppingCartAttributeItemServiceImpl(ShoppingCartAttributeItemRepository shoppingCartAttributeItemRepository,
			ShoppingCartAttributeItemSearchRepository shoppingCartAttributeItemSearchRepository) {
		super(shoppingCartAttributeItemRepository, shoppingCartAttributeItemSearchRepository);
		this.shoppingCartAttributeItemRepository = shoppingCartAttributeItemRepository;
		this.shoppingCartAttributeItemSearchRepository = shoppingCartAttributeItemSearchRepository;
	}

}
