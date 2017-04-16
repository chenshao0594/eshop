package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Template;
import com.smartshop.eshop.repository.TemplateRepository;
import com.smartshop.eshop.repository.search.TemplateSearchRepository;
import com.smartshop.eshop.service.TemplateService;

/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateServiceImpl extends AbstractDomainServiceImpl<Template, Long> implements TemplateService {

	private final Logger LOGGER = LoggerFactory.getLogger(TemplateServiceImpl.class);
	private final TemplateRepository templateRepository;
	private final TemplateSearchRepository templateSearchRepository;

	public TemplateServiceImpl(TemplateRepository templateRepository,
			TemplateSearchRepository templateSearchRepository) {
		super(templateRepository, templateSearchRepository);
		this.templateRepository = templateRepository;
		this.templateSearchRepository = templateSearchRepository;
	}

}
