package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductPriceDescription;
import com.smartshop.eshop.service.ProductPriceDescriptionService;

/**
 * REST controller for managing ProductPriceDescription.
 */
@RestController
@RequestMapping("/api/" + ProductPriceDescriptionController.SECTION_KEY)
public class ProductPriceDescriptionController extends AbstractDomainController<ProductPriceDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductPriceDescriptionController.class);
	public static final String SECTION_KEY = "product-price-descriptions";
	private static final String ENTITY_NAME = "productPriceDescription";

	private final ProductPriceDescriptionService productPriceDescriptionService;

	public ProductPriceDescriptionController(ProductPriceDescriptionService productPriceDescriptionService) {
		super(productPriceDescriptionService);
		this.productPriceDescriptionService = productPriceDescriptionService;
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