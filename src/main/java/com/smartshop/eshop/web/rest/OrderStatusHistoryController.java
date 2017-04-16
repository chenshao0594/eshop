package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderStatusHistory;
import com.smartshop.eshop.service.OrderStatusHistoryService;

/**
 * REST controller for managing OrderStatusHistory.
 */
@RestController
@RequestMapping("/api/" + OrderStatusHistoryController.SECTION_KEY)
public class OrderStatusHistoryController extends AbstractDomainController<OrderStatusHistory, Long> {

	private final Logger log = LoggerFactory.getLogger(OrderStatusHistoryController.class);
	public static final String SECTION_KEY = "order-status-histories";
	private static final String ENTITY_NAME = "orderStatusHistory";

	private final OrderStatusHistoryService orderStatusHistoryService;

	public OrderStatusHistoryController(OrderStatusHistoryService orderStatusHistoryService) {
		super(orderStatusHistoryService);
		this.orderStatusHistoryService = orderStatusHistoryService;
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