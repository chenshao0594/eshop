package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderProductPrice;
import com.smartshop.eshop.service.OrderProductPriceService;

/**
 * REST controller for managing OrderProductPrice.
 */
@RestController
@RequestMapping("/api/" + OrderProductPriceController.SECTION_KEY)
public class OrderProductPriceController extends AbstractDomainController<OrderProductPrice, Long> {

	private final Logger log = LoggerFactory.getLogger(OrderProductPriceController.class);
	public static final String SECTION_KEY = "order-product-prices";
	private static final String ENTITY_NAME = "orderProductPrice";

	private final OrderProductPriceService orderProductPriceService;

	public OrderProductPriceController(OrderProductPriceService orderProductPriceService) {
		super(orderProductPriceService);
		this.orderProductPriceService = orderProductPriceService;
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