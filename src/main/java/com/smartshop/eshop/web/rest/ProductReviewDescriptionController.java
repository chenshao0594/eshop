package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductReviewDescription;
import com.smartshop.eshop.service.ProductReviewDescriptionService;

/**
 * REST controller for managing ProductReviewDescription.
 */
@RestController
@RequestMapping("/api/" + ProductReviewDescriptionController.SECTION_KEY)
public class ProductReviewDescriptionController extends AbstractDomainController<ProductReviewDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductReviewDescriptionController.class);
	public static final String SECTION_KEY = "product-review-descriptions";
	private static final String ENTITY_NAME = "productReviewDescription";

	private final ProductReviewDescriptionService productReviewDescriptionService;

	public ProductReviewDescriptionController(ProductReviewDescriptionService productReviewDescriptionService) {
		super(productReviewDescriptionService);
		this.productReviewDescriptionService = productReviewDescriptionService;
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