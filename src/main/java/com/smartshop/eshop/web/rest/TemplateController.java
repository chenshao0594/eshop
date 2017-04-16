package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Template;
import com.smartshop.eshop.service.TemplateService;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api/" + TemplateController.SECTION_KEY)
public class TemplateController extends AbstractDomainController<Template, Long> {

	private final Logger log = LoggerFactory.getLogger(TemplateController.class);
	public static final String SECTION_KEY = "templates";
	private static final String ENTITY_NAME = "template";

	private final TemplateService templateService;

	public TemplateController(TemplateService templateService) {
		super(templateService);
		this.templateService = templateService;
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