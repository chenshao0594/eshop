package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderStatusHistory;
import com.smartshop.eshop.repository.OrderStatusHistoryRepository;
import com.smartshop.eshop.repository.search.OrderStatusHistorySearchRepository;
import com.smartshop.eshop.service.OrderStatusHistoryService;

/**
 * Service Implementation for managing OrderStatusHistory.
 */
@Service
@Transactional
public class OrderStatusHistoryServiceImpl extends AbstractDomainServiceImpl<OrderStatusHistory, Long>
		implements OrderStatusHistoryService {

	private final Logger LOGGER = LoggerFactory.getLogger(OrderStatusHistoryServiceImpl.class);
	private final OrderStatusHistoryRepository orderStatusHistoryRepository;
	private final OrderStatusHistorySearchRepository orderStatusHistorySearchRepository;

	public OrderStatusHistoryServiceImpl(OrderStatusHistoryRepository orderStatusHistoryRepository,
			OrderStatusHistorySearchRepository orderStatusHistorySearchRepository) {
		super(orderStatusHistoryRepository, orderStatusHistorySearchRepository);
		this.orderStatusHistoryRepository = orderStatusHistoryRepository;
		this.orderStatusHistorySearchRepository = orderStatusHistorySearchRepository;
	}

}
