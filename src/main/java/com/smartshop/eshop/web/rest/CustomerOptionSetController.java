package com.smartshop.eshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.CustomerOptionSet;
import com.smartshop.eshop.service.CustomerOptionSetService;
import com.smartshop.eshop.web.rest.util.HeaderUtil;
import com.smartshop.eshop.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CustomerOptionSet.
 */
@RestController
@RequestMapping("/api/"+ CustomerOptionSetController.SECTION_KEY)
public class CustomerOptionSetController extends AbstractDomainController< CustomerOptionSet, Long>{

    private final Logger log = LoggerFactory.getLogger(CustomerOptionSetController.class);
    public static final String SECTION_KEY = "customer-option-sets";
    private static final String ENTITY_NAME = "customerOptionSet";
        
     private final CustomerOptionSetService customerOptionSetService;

    public CustomerOptionSetController(CustomerOptionSetService customerOptionSetService) {
        super(customerOptionSetService);
        this.customerOptionSetService = customerOptionSetService;
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