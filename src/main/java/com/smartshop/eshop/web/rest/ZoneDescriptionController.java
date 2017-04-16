package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.service.ZoneDescriptionService;

/**
 * REST controller for managing ZoneDescription.
 */
@RestController
@RequestMapping("/api/" + ZoneDescriptionController.SECTION_KEY)
public class ZoneDescriptionController extends AbstractDomainController<ZoneDescription, Long> {

	private final Logger log = LoggerFactory.getLogger(ZoneDescriptionController.class);
	public static final String SECTION_KEY = "zone-descriptions";
	private static final String ENTITY_NAME = "zoneDescription";

	private final ZoneDescriptionService zoneDescriptionService;

	public ZoneDescriptionController(ZoneDescriptionService zoneDescriptionService) {
		super(zoneDescriptionService);
		this.zoneDescriptionService = zoneDescriptionService;
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