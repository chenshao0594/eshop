package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductImage;
import com.smartshop.eshop.service.ProductImageService;

/**
 * REST controller for managing ProductImage.
 */
@RestController
@RequestMapping("/api/" + ProductImageController.SECTION_KEY)
public class ProductImageController extends AbstractDomainController<ProductImage, Long> {

	private final Logger log = LoggerFactory.getLogger(ProductImageController.class);
	public static final String SECTION_KEY = "product-images";
	private static final String ENTITY_NAME = "productImage";

	private final ProductImageService productImageService;

	public ProductImageController(ProductImageService productImageService) {
		super(productImageService);
		this.productImageService = productImageService;
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