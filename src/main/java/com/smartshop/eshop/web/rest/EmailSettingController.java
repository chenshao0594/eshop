package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.EmailSetting;
import com.smartshop.eshop.service.EmailSettingService;

/**
 * REST controller for managing EmailSetting.
 */
@RestController
@RequestMapping("/api/" + EmailSettingController.SECTION_KEY)
public class EmailSettingController extends AbstractDomainController<EmailSetting, Long> {

	private final Logger log = LoggerFactory.getLogger(EmailSettingController.class);
	public static final String SECTION_KEY = "email-settings";
	private static final String ENTITY_NAME = "emailSetting";

	private final EmailSettingService emailSettingService;

	public EmailSettingController(EmailSettingService emailSettingService) {
		super(emailSettingService);
		this.emailSettingService = emailSettingService;
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