package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.SystemNotification;
import com.smartshop.eshop.service.SystemNotificationService;

/**
 * REST controller for managing SystemNotification.
 */
@RestController
@RequestMapping("/api/" + SystemNotificationController.SECTION_KEY)
public class SystemNotificationController extends AbstractDomainController<SystemNotification, Long> {

	private final Logger log = LoggerFactory.getLogger(SystemNotificationController.class);
	public static final String SECTION_KEY = "system-notifications";
	private static final String ENTITY_NAME = "systemNotification";

	private final SystemNotificationService systemNotificationService;

	public SystemNotificationController(SystemNotificationService systemNotificationService) {
		super(systemNotificationService);
		this.systemNotificationService = systemNotificationService;
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