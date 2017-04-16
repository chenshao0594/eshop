package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ManufacturerDescription;
import com.smartshop.eshop.service.ManufacturerDescriptionService;

/**
 * REST controller for managing ManufacturerDescription.
 */
@RestController
@RequestMapping("/api/"+ ManufacturerDescriptionController.SECTION_KEY)
public class ManufacturerDescriptionController extends AbstractDomainController< ManufacturerDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(ManufacturerDescriptionController.class);
    public static final String SECTION_KEY = "manufacturer-descriptions";
    private static final String ENTITY_NAME = "manufacturerDescription";
        
     private final ManufacturerDescriptionService manufacturerDescriptionService;

    public ManufacturerDescriptionController(ManufacturerDescriptionService manufacturerDescriptionService) {
        super(manufacturerDescriptionService);
        this.manufacturerDescriptionService = manufacturerDescriptionService;
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