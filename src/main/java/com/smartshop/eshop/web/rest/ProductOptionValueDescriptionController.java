package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductOptionValueDescription;
import com.smartshop.eshop.service.ProductOptionValueDescriptionService;

/**
 * REST controller for managing ProductOptionValueDescription.
 */
@RestController
@RequestMapping("/api/" + ProductOptionValueDescriptionController.SECTION_KEY)
public class ProductOptionValueDescriptionController
		extends AbstractDomainController<ProductOptionValueDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductOptionValueDescriptionController.class);
	public static final String SECTION_KEY = "product-option-value-descriptions";
	private static final String ENTITY_NAME = "productOptionValueDescription";

	private final ProductOptionValueDescriptionService productOptionValueDescriptionService;

	public ProductOptionValueDescriptionController(
			ProductOptionValueDescriptionService productOptionValueDescriptionService) {
		super(productOptionValueDescriptionService);
		this.productOptionValueDescriptionService = productOptionValueDescriptionService;
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