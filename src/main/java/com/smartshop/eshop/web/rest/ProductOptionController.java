package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductOption;
import com.smartshop.eshop.service.ProductOptionService;

/**
 * REST controller for managing ProductOption.
 */
@RestController
@RequestMapping("/api/" + ProductOptionController.SECTION_KEY)
public class ProductOptionController extends AbstractDomainController<ProductOption, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductOptionController.class);
	public static final String SECTION_KEY = "product-options";
	private static final String ENTITY_NAME = "productOption";

	private final ProductOptionService productOptionService;

	public ProductOptionController(ProductOptionService productOptionService) {
		super(productOptionService);
		this.productOptionService = productOptionService;
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