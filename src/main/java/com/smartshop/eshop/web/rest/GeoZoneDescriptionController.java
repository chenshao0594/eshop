package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.GeoZoneDescription;
import com.smartshop.eshop.service.GeoZoneDescriptionService;

/**
 * REST controller for managing GeoZoneDescription.
 */
@RestController
@RequestMapping("/api/"+ GeoZoneDescriptionController.SECTION_KEY)
public class GeoZoneDescriptionController extends AbstractDomainController< GeoZoneDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(GeoZoneDescriptionController.class);
    public static final String SECTION_KEY = "geo-zone-descriptions";
    private static final String ENTITY_NAME = "geoZoneDescription";
        
     private final GeoZoneDescriptionService geoZoneDescriptionService;

    public GeoZoneDescriptionController(GeoZoneDescriptionService geoZoneDescriptionService) {
        super(geoZoneDescriptionService);
        this.geoZoneDescriptionService = geoZoneDescriptionService;
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