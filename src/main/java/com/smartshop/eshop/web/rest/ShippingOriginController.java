package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.service.ShippingOriginService;

/**
 * REST controller for managing ShippingOrigin.
 */
@RestController
@RequestMapping("/api/" + ShippingOriginController.SECTION_KEY)
public class ShippingOriginController extends AbstractDomainController<ShippingOrigin, Long> {

	private final Logger log = LoggerFactory.getLogger(ShippingOriginController.class);
	public static final String SECTION_KEY = "shipping-origins";
	private static final String ENTITY_NAME = "shippingOrigin";

	private final ShippingOriginService shippingOriginService;

	public ShippingOriginController(ShippingOriginService shippingOriginService) {
		super(shippingOriginService);
		this.shippingOriginService = shippingOriginService;
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