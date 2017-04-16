package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.EmailSettingService;
import com.smartshop.eshop.domain.EmailSetting;
import com.smartshop.eshop.repository.EmailSettingRepository;
import com.smartshop.eshop.repository.search.EmailSettingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing EmailSetting.
 */
@Service
@Transactional
public class EmailSettingServiceImpl extends AbstractDomainServiceImpl<EmailSetting, Long>
		implements EmailSettingService {

	private final Logger LOGGER = LoggerFactory.getLogger(EmailSettingServiceImpl.class);
	private final EmailSettingRepository emailSettingRepository;
	private final EmailSettingSearchRepository emailSettingSearchRepository;

	public EmailSettingServiceImpl(EmailSettingRepository emailSettingRepository,
			EmailSettingSearchRepository emailSettingSearchRepository) {
		super(emailSettingRepository, emailSettingSearchRepository);
		this.emailSettingRepository = emailSettingRepository;
		this.emailSettingSearchRepository = emailSettingSearchRepository;
	}

}
