package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.CategoryDescription;
import com.smartshop.eshop.service.CategoryDescriptionService;

/**
 * REST controller for managing CategoryDescription.
 */
@RestController
@RequestMapping("/api/" + CategoryDescriptionController.SECTION_KEY)
public class CategoryDescriptionController extends AbstractDomainController<CategoryDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(CategoryDescriptionController.class);
	public static final String SECTION_KEY = "category-descriptions";
	private static final String ENTITY_NAME = "categoryDescription";

	private final CategoryDescriptionService categoryDescriptionService;

	public CategoryDescriptionController(CategoryDescriptionService categoryDescriptionService) {
		super(categoryDescriptionService);
		this.categoryDescriptionService = categoryDescriptionService;
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