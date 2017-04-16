package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.GeoZoneDescription;
import com.smartshop.eshop.repository.GeoZoneDescriptionRepository;
import com.smartshop.eshop.repository.search.GeoZoneDescriptionSearchRepository;
import com.smartshop.eshop.service.GeoZoneDescriptionService;

/**
 * Service Implementation for managing GeoZoneDescription.
 */
@Service
@Transactional
public class GeoZoneDescriptionServiceImpl extends AbstractDomainServiceImpl<GeoZoneDescription, Long>
		implements GeoZoneDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(GeoZoneDescriptionServiceImpl.class);
	private final GeoZoneDescriptionRepository geoZoneDescriptionRepository;
	private final GeoZoneDescriptionSearchRepository geoZoneDescriptionSearchRepository;

	public GeoZoneDescriptionServiceImpl(GeoZoneDescriptionRepository geoZoneDescriptionRepository,
			GeoZoneDescriptionSearchRepository geoZoneDescriptionSearchRepository) {
		super(geoZoneDescriptionRepository, geoZoneDescriptionSearchRepository);
		this.geoZoneDescriptionRepository = geoZoneDescriptionRepository;
		this.geoZoneDescriptionSearchRepository = geoZoneDescriptionSearchRepository;
	}

}
