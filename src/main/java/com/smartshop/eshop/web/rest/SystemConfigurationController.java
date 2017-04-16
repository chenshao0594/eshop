package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.SystemConfiguration;
import com.smartshop.eshop.service.SystemConfigurationService;

/**
 * REST controller for managing SystemConfiguration.
 */
@RestController
@RequestMapping("/api/"+ SystemConfigurationController.SECTION_KEY)
public class SystemConfigurationController extends AbstractDomainController< SystemConfiguration, Long>{

    private final Logger log = LoggerFactory.getLogger(SystemConfigurationController.class);
    public static final String SECTION_KEY = "system-configurations";
    private static final String ENTITY_NAME = "systemConfiguration";
        
     private final SystemConfigurationService systemConfigurationService;

    public SystemConfigurationController(SystemConfigurationService systemConfigurationService) {
        super(systemConfigurationService);
        this.systemConfigurationService = systemConfigurationService;
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