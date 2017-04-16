package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductDescription;
import com.smartshop.eshop.service.ProductDescriptionService;

/**
 * REST controller for managing ProductDescription.
 */
@RestController
@RequestMapping("/api/" + ProductDescriptionController.SECTION_KEY)
public class ProductDescriptionController extends AbstractDomainController<ProductDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductDescriptionController.class);
	public static final String SECTION_KEY = "product-descriptions";
	private static final String ENTITY_NAME = "productDescription";

	private final ProductDescriptionService productDescriptionService;

	public ProductDescriptionController(ProductDescriptionService productDescriptionService) {
		super(productDescriptionService);
		this.productDescriptionService = productDescriptionService;
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