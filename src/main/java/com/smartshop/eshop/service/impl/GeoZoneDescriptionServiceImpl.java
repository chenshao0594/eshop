package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.GeoZoneDescriptionService;
import com.smartshop.eshop.domain.GeoZoneDescription;
import com.smartshop.eshop.repository.GeoZoneDescriptionRepository;
import com.smartshop.eshop.repository.search.GeoZoneDescriptionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GeoZoneDescription.
 */
@Service
@Transactional
public class GeoZoneDescriptionServiceImpl extends AbstractDomainServiceImpl< GeoZoneDescription, Long> implements GeoZoneDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(GeoZoneDescriptionServiceImpl.class);
    private final GeoZoneDescriptionRepository geoZoneDescriptionRepository;
    private final GeoZoneDescriptionSearchRepository geoZoneDescriptionSearchRepository;
    
    public GeoZoneDescriptionServiceImpl(GeoZoneDescriptionRepository geoZoneDescriptionRepository, GeoZoneDescriptionSearchRepository geoZoneDescriptionSearchRepository) {
        super(geoZoneDescriptionRepository,geoZoneDescriptionSearchRepository);
        this.geoZoneDescriptionRepository = geoZoneDescriptionRepository;
        this.geoZoneDescriptionSearchRepository = geoZoneDescriptionSearchRepository;
    }
    
}
