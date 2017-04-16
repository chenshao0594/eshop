package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.repository.ZoneRepository;
import com.smartshop.eshop.repository.search.ZoneSearchRepository;
import com.smartshop.eshop.service.ZoneService;

/**
 * Service Implementation for managing Zone.
 */
@Service
@Transactional
public class ZoneServiceImpl extends AbstractDomainServiceImpl<Zone, Long> implements ZoneService {

	private final Logger LOGGER = LoggerFactory.getLogger(ZoneServiceImpl.class);
	private final ZoneRepository zoneRepository;
	private final ZoneSearchRepository zoneSearchRepository;

	public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneSearchRepository zoneSearchRepository) {
		super(zoneRepository, zoneSearchRepository);
		this.zoneRepository = zoneRepository;
		this.zoneSearchRepository = zoneSearchRepository;
	}

}
