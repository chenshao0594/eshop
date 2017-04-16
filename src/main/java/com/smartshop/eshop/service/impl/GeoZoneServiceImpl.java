package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.GeoZone;
import com.smartshop.eshop.repository.GeoZoneRepository;
import com.smartshop.eshop.repository.search.GeoZoneSearchRepository;
import com.smartshop.eshop.service.GeoZoneService;

/**
 * Service Implementation for managing GeoZone.
 */
@Service
@Transactional
public class GeoZoneServiceImpl extends AbstractDomainServiceImpl<GeoZone, Long> implements GeoZoneService {

	private final Logger LOGGER = LoggerFactory.getLogger(GeoZoneServiceImpl.class);
	private final GeoZoneRepository geoZoneRepository;
	private final GeoZoneSearchRepository geoZoneSearchRepository;

	public GeoZoneServiceImpl(GeoZoneRepository geoZoneRepository, GeoZoneSearchRepository geoZoneSearchRepository) {
		super(geoZoneRepository, geoZoneSearchRepository);
		this.geoZoneRepository = geoZoneRepository;
		this.geoZoneSearchRepository = geoZoneSearchRepository;
	}

}
