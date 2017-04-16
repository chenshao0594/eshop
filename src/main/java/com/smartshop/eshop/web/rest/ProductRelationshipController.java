package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductRelationship;
import com.smartshop.eshop.service.ProductRelationshipService;

/**
 * REST controller for managing ProductRelationship.
 */
@RestController
@RequestMapping("/api/" + ProductRelationshipController.SECTION_KEY)
public class ProductRelationshipController extends AbstractDomainController<ProductRelationship, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductRelationshipController.class);
	public static final String SECTION_KEY = "product-relationships";
	private static final String ENTITY_NAME = "productRelationship";

	private final ProductRelationshipService productRelationshipService;

	public ProductRelationshipController(ProductRelationshipService productRelationshipService) {
		super(productRelationshipService);
		this.productRelationshipService = productRelationshipService;
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