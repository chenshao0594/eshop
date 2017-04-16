package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.repository.OrderTotalRepository;
import com.smartshop.eshop.repository.search.OrderTotalSearchRepository;
import com.smartshop.eshop.service.OrderTotalService;

/**
 * Service Implementation for managing OrderTotal.
 */
@Service
@Transactional
public class OrderTotalServiceImpl extends AbstractDomainServiceImpl<OrderTotal, Long> implements OrderTotalService {

	private final Logger LOGGER = LoggerFactory.getLogger(OrderTotalServiceImpl.class);
	private final OrderTotalRepository orderTotalRepository;
	private final OrderTotalSearchRepository orderTotalSearchRepository;

	public OrderTotalServiceImpl(OrderTotalRepository orderTotalRepository,
			OrderTotalSearchRepository orderTotalSearchRepository) {
		super(orderTotalRepository, orderTotalSearchRepository);
		this.orderTotalRepository = orderTotalRepository;
		this.orderTotalSearchRepository = orderTotalSearchRepository;
	}

}
