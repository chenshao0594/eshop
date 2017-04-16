package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.service.MerchantConfigurationService;

/**
 * REST controller for managing MerchantConfiguration.
 */
@RestController
@RequestMapping("/api/" + MerchantConfigurationController.SECTION_KEY)
public class MerchantConfigurationController extends AbstractDomainController<MerchantConfiguration, Long> {

	private final Logger log = LoggerFactory.getLogger(MerchantConfigurationController.class);
	public static final String SECTION_KEY = "merchant-configurations";
	private static final String ENTITY_NAME = "merchantConfiguration";

	private final MerchantConfigurationService merchantConfigurationService;

	public MerchantConfigurationController(MerchantConfigurationService merchantConfigurationService) {
		super(merchantConfigurationService);
		this.merchantConfigurationService = merchantConfigurationService;
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