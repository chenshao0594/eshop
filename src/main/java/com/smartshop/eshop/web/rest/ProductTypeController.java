package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductType;
import com.smartshop.eshop.service.ProductTypeService;

/**
 * REST controller for managing ProductType.
 */
@RestController
@RequestMapping("/api/" + ProductTypeController.SECTION_KEY)
public class ProductTypeController extends AbstractDomainController<ProductType, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductTypeController.class);
	public static final String SECTION_KEY = "product-types";
	private static final String ENTITY_NAME = "productType";

	private final ProductTypeService productTypeService;

	public ProductTypeController(ProductTypeService productTypeService) {
		super(productTypeService);
		this.productTypeService = productTypeService;
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