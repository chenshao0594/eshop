package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.repository.IntegrationModuleRepository;
import com.smartshop.eshop.repository.search.IntegrationModuleSearchRepository;
import com.smartshop.eshop.service.IntegrationModuleService;

/**
 * Service Implementation for managing IntegrationModule.
 */
@Service
@Transactional
public class IntegrationModuleServiceImpl extends AbstractDomainServiceImpl<IntegrationModule, Long>
		implements IntegrationModuleService {

	private final Logger LOGGER = LoggerFactory.getLogger(IntegrationModuleServiceImpl.class);
	private final IntegrationModuleRepository integrationModuleRepository;
	private final IntegrationModuleSearchRepository integrationModuleSearchRepository;

	public IntegrationModuleServiceImpl(IntegrationModuleRepository integrationModuleRepository,
			IntegrationModuleSearchRepository integrationModuleSearchRepository) {
		super(integrationModuleRepository, integrationModuleSearchRepository);
		this.integrationModuleRepository = integrationModuleRepository;
		this.integrationModuleSearchRepository = integrationModuleSearchRepository;
	}

}
