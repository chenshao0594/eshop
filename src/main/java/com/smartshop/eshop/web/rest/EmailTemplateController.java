package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.EmailTemplate;
import com.smartshop.eshop.service.EmailTemplateService;

/**
 * REST controller for managing EmailTemplate.
 */
@RestController
@RequestMapping("/api/" + EmailTemplateController.SECTION_KEY)
public class EmailTemplateController extends AbstractDomainController<EmailTemplate, Long> {

	private final Logger log = LoggerFactory.getLogger(EmailTemplateController.class);
	public static final String SECTION_KEY = "email-templates";
	private static final String ENTITY_NAME = "emailTemplate";

	private final EmailTemplateService emailTemplateService;

	public EmailTemplateController(EmailTemplateService emailTemplateService) {
		super(emailTemplateService);
		this.emailTemplateService = emailTemplateService;
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