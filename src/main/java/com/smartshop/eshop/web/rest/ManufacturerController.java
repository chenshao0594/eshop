package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Manufacturer;
import com.smartshop.eshop.service.ManufacturerService;

/**
 * REST controller for managing Manufacturer.
 */
@RestController
@RequestMapping("/api/"+ ManufacturerController.SECTION_KEY)
public class ManufacturerController extends AbstractDomainController< Manufacturer, Long>{

    private final Logger log = LoggerFactory.getLogger(ManufacturerController.class);
    public static final String SECTION_KEY = "manufacturers";
    private static final String ENTITY_NAME = "manufacturer";
        
     private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        super(manufacturerService);
        this.manufacturerService = manufacturerService;
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