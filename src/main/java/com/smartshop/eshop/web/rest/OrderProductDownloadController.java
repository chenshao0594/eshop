package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.OrderProductDownload;
import com.smartshop.eshop.service.OrderProductDownloadService;

/**
 * REST controller for managing OrderProductDownload.
 */
@RestController
@RequestMapping("/api/" + OrderProductDownloadController.SECTION_KEY)
public class OrderProductDownloadController extends AbstractDomainController<OrderProductDownload, Long> {

	private final Logger log = LoggerFactory.getLogger(OrderProductDownloadController.class);
	public static final String SECTION_KEY = "order-product-downloads";
	private static final String ENTITY_NAME = "orderProductDownload";

	private final OrderProductDownloadService orderProductDownloadService;

	public OrderProductDownloadController(OrderProductDownloadService orderProductDownloadService) {
		super(orderProductDownloadService);
		this.orderProductDownloadService = orderProductDownloadService;
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