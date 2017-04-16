package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.MerchantLog;
import com.smartshop.eshop.repository.MerchantLogRepository;
import com.smartshop.eshop.repository.search.MerchantLogSearchRepository;
import com.smartshop.eshop.service.MerchantLogService;

/**
 * Service Implementation for managing MerchantLog.
 */
@Service
@Transactional
public class MerchantLogServiceImpl extends AbstractDomainServiceImpl<MerchantLog, Long> implements MerchantLogService {

	private final Logger LOGGER = LoggerFactory.getLogger(MerchantLogServiceImpl.class);
	private final MerchantLogRepository merchantLogRepository;
	private final MerchantLogSearchRepository merchantLogSearchRepository;

	public MerchantLogServiceImpl(MerchantLogRepository merchantLogRepository,
			MerchantLogSearchRepository merchantLogSearchRepository) {
		super(merchantLogRepository, merchantLogSearchRepository);
		this.merchantLogRepository = merchantLogRepository;
		this.merchantLogSearchRepository = merchantLogSearchRepository;
	}

}
