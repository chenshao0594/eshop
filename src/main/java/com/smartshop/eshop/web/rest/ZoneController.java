package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.service.ZoneService;

/**
 * REST controller for managing Zone.
 */
@RestController
@RequestMapping("/api/" + ZoneController.SECTION_KEY)
public class ZoneController extends AbstractDomainController<Zone, Long> {

	private final Logger log = LoggerFactory.getLogger(ZoneController.class);
	public static final String SECTION_KEY = "zones";
	private static final String ENTITY_NAME = "zone";

	private final ZoneService zoneService;

	public ZoneController(ZoneService zoneService) {
		super(zoneService);
		this.zoneService = zoneService;
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