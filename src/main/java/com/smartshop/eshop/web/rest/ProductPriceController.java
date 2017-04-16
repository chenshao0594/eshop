package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductPrice;
import com.smartshop.eshop.service.ProductPriceService;

/**
 * REST controller for managing ProductPrice.
 */
@RestController
@RequestMapping("/api/" + ProductPriceController.SECTION_KEY)
public class ProductPriceController extends AbstractDomainController<ProductPrice, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductPriceController.class);
	public static final String SECTION_KEY = "product-prices";
	private static final String ENTITY_NAME = "productPrice";

	private final ProductPriceService productPriceService;

	public ProductPriceController(ProductPriceService productPriceService) {
		super(productPriceService);
		this.productPriceService = productPriceService;
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