package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.GeoZone;
import com.smartshop.eshop.service.GeoZoneService;

/**
 * REST controller for managing GeoZone.
 */
@RestController
@RequestMapping("/api/"+ GeoZoneController.SECTION_KEY)
public class GeoZoneController extends AbstractDomainController< GeoZone, Long>{

    private final Logger log = LoggerFactory.getLogger(GeoZoneController.class);
    public static final String SECTION_KEY = "geo-zones";
    private static final String ENTITY_NAME = "geoZone";
        
     private final GeoZoneService geoZoneService;

    public GeoZoneController(GeoZoneService geoZoneService) {
        super(geoZoneService);
        this.geoZoneService = geoZoneService;
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