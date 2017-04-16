package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ContentDescription;
import com.smartshop.eshop.service.ContentDescriptionService;

/**
 * REST controller for managing ContentDescription.
 */
@RestController
@RequestMapping("/api/"+ ContentDescriptionController.SECTION_KEY)
public class ContentDescriptionController extends AbstractDomainController< ContentDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(ContentDescriptionController.class);
    public static final String SECTION_KEY = "content-descriptions";
    private static final String ENTITY_NAME = "contentDescription";
        
     private final ContentDescriptionService contentDescriptionService;

    public ContentDescriptionController(ContentDescriptionService contentDescriptionService) {
        super(contentDescriptionService);
        this.contentDescriptionService = contentDescriptionService;
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