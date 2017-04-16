package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.service.IntegrationModuleService;

/**
 * REST controller for managing IntegrationModule.
 */
@RestController
@RequestMapping("/api/"+ IntegrationModuleController.SECTION_KEY)
public class IntegrationModuleController extends AbstractDomainController< IntegrationModule, Long>{

    private final Logger log = LoggerFactory.getLogger(IntegrationModuleController.class);
    public static final String SECTION_KEY = "integration-modules";
    private static final String ENTITY_NAME = "integrationModule";
        
     private final IntegrationModuleService integrationModuleService;

    public IntegrationModuleController(IntegrationModuleService integrationModuleService) {
        super(integrationModuleService);
        this.integrationModuleService = integrationModuleService;
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