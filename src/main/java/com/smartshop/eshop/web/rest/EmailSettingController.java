package com.smartshop.eshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.EmailSetting;
import com.smartshop.eshop.service.EmailSettingService;
import com.smartshop.eshop.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EmailSetting.
 */
@RestController
@RequestMapping("/api/"+ EmailSettingController.SECTION_KEY)
public class EmailSettingController extends AbstractDomainController< EmailSetting, Long>{

    private final Logger log = LoggerFactory.getLogger(EmailSettingController.class);
    public static final String SECTION_KEY = "email-settings";
    private static final String ENTITY_NAME = "emailSetting";
        
     private final EmailSettingService emailSettingService;

    public EmailSettingController(EmailSettingService emailSettingService) {
        super(emailSettingService);
        this.emailSettingService = emailSettingService;
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