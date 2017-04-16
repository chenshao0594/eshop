package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.service.LanguageService;

/**
 * REST controller for managing Language.
 */
@RestController
@RequestMapping("/api/"+ LanguageController.SECTION_KEY)
public class LanguageController extends AbstractDomainController< Language, Long>{

    private final Logger log = LoggerFactory.getLogger(LanguageController.class);
    public static final String SECTION_KEY = "languages";
    private static final String ENTITY_NAME = "language";
        
     private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        super(languageService);
        this.languageService = languageService;
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