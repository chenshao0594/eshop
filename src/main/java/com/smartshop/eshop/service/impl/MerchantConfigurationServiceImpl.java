package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.repository.MerchantConfigurationRepository;
import com.smartshop.eshop.repository.search.MerchantConfigurationSearchRepository;
import com.smartshop.eshop.service.MerchantConfigurationService;

/**
 * Service Implementation for managing MerchantConfiguration.
 */
@Service
@Transactional
public class MerchantConfigurationServiceImpl extends AbstractDomainServiceImpl<MerchantConfiguration, Long>
		implements MerchantConfigurationService {

	private final Logger LOGGER = LoggerFactory.getLogger(MerchantConfigurationServiceImpl.class);
	private final MerchantConfigurationRepository merchantConfigurationRepository;
	private final MerchantConfigurationSearchRepository merchantConfigurationSearchRepository;

	public MerchantConfigurationServiceImpl(MerchantConfigurationRepository merchantConfigurationRepository,
			MerchantConfigurationSearchRepository merchantConfigurationSearchRepository) {
		super(merchantConfigurationRepository, merchantConfigurationSearchRepository);
		this.merchantConfigurationRepository = merchantConfigurationRepository;
		this.merchantConfigurationSearchRepository = merchantConfigurationSearchRepository;
	}

}
