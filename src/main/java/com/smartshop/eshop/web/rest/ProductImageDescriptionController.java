package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductImageDescription;
import com.smartshop.eshop.service.ProductImageDescriptionService;

/**
 * REST controller for managing ProductImageDescription.
 */
@RestController
@RequestMapping("/api/" + ProductImageDescriptionController.SECTION_KEY)
public class ProductImageDescriptionController extends AbstractDomainController<ProductImageDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductImageDescriptionController.class);
	public static final String SECTION_KEY = "product-image-descriptions";
	private static final String ENTITY_NAME = "productImageDescription";

	private final ProductImageDescriptionService productImageDescriptionService;

	public ProductImageDescriptionController(ProductImageDescriptionService productImageDescriptionService) {
		super(productImageDescriptionService);
		this.productImageDescriptionService = productImageDescriptionService;
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