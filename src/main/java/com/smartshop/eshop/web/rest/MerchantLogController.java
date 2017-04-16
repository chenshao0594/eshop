package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.MerchantLog;
import com.smartshop.eshop.service.MerchantLogService;

/**
 * REST controller for managing MerchantLog.
 */
@RestController
@RequestMapping("/api/" + MerchantLogController.SECTION_KEY)
public class MerchantLogController extends AbstractDomainController<MerchantLog, Long> {

	private final Logger log = LoggerFactory.getLogger(MerchantLogController.class);
	public static final String SECTION_KEY = "merchant-logs";
	private static final String ENTITY_NAME = "merchantLog";

	private final MerchantLogService merchantLogService;

	public MerchantLogController(MerchantLogService merchantLogService) {
		super(merchantLogService);
		this.merchantLogService = merchantLogService;
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