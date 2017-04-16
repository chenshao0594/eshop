package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.EmailTemplateService;
import com.smartshop.eshop.domain.EmailTemplate;
import com.smartshop.eshop.repository.EmailTemplateRepository;
import com.smartshop.eshop.repository.search.EmailTemplateSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing EmailTemplate.
 */
@Service
@Transactional
public class EmailTemplateServiceImpl extends AbstractDomainServiceImpl<EmailTemplate, Long>
		implements EmailTemplateService {

	private final Logger LOGGER = LoggerFactory.getLogger(EmailTemplateServiceImpl.class);
	private final EmailTemplateRepository emailTemplateRepository;
	private final EmailTemplateSearchRepository emailTemplateSearchRepository;

	public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository,
			EmailTemplateSearchRepository emailTemplateSearchRepository) {
		super(emailTemplateRepository, emailTemplateSearchRepository);
		this.emailTemplateRepository = emailTemplateRepository;
		this.emailTemplateSearchRepository = emailTemplateSearchRepository;
	}

}
