package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.repository.ZoneDescriptionRepository;
import com.smartshop.eshop.repository.search.ZoneDescriptionSearchRepository;
import com.smartshop.eshop.service.ZoneDescriptionService;

/**
 * Service Implementation for managing ZoneDescription.
 */
@Service
@Transactional
public class ZoneDescriptionServiceImpl extends AbstractDomainServiceImpl<ZoneDescription, Long>
		implements ZoneDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ZoneDescriptionServiceImpl.class);
	private final ZoneDescriptionRepository zoneDescriptionRepository;
	private final ZoneDescriptionSearchRepository zoneDescriptionSearchRepository;

	public ZoneDescriptionServiceImpl(ZoneDescriptionRepository zoneDescriptionRepository,
			ZoneDescriptionSearchRepository zoneDescriptionSearchRepository) {
		super(zoneDescriptionRepository, zoneDescriptionSearchRepository);
		this.zoneDescriptionRepository = zoneDescriptionRepository;
		this.zoneDescriptionSearchRepository = zoneDescriptionSearchRepository;
	}

}
