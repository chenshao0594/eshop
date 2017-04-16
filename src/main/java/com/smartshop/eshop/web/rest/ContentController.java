package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Content;
import com.smartshop.eshop.service.ContentService;

/**
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api/" + ContentController.SECTION_KEY)
public class ContentController extends AbstractDomainController<Content, Long> {

	private final Logger log = LoggerFactory.getLogger(ContentController.class);
	public static final String SECTION_KEY = "contents";
	private static final String ENTITY_NAME = "content";

	private final ContentService contentService;

	public ContentController(ContentService contentService) {
		super(contentService);
		this.contentService = contentService;
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