package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.service.SalesOrderService;

/**
 * REST controller for managing SalesOrder.
 */
@RestController
@RequestMapping("/api/" + SalesOrderController.SECTION_KEY)
public class SalesOrderController extends AbstractDomainController<SalesOrder, Long> {

	private final Logger log = LoggerFactory.getLogger(SalesOrderController.class);
	public static final String SECTION_KEY = "sales-orders";
	private static final String ENTITY_NAME = "salesOrder";

	private final SalesOrderService salesOrderService;

	public SalesOrderController(SalesOrderService salesOrderService) {
		super(salesOrderService);
		this.salesOrderService = salesOrderService;
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