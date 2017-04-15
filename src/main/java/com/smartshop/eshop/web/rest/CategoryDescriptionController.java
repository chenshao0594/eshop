package com.smartshop.eshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.CategoryDescription;
import com.smartshop.eshop.service.CategoryDescriptionService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CategoryDescription.
 */
@RestController
@RequestMapping("/api/"+ CategoryDescriptionController.SECTION_KEY)
public class CategoryDescriptionController extends AbstractDomainController< CategoryDescription, Long>{

    private final Logger log = LoggerFactory.getLogger(CategoryDescriptionController.class);
    public static final String SECTION_KEY = "category-descriptions";
    private static final String ENTITY_NAME = "categoryDescription";
        
     private final CategoryDescriptionService categoryDescriptionService;

    public CategoryDescriptionController(CategoryDescriptionService categoryDescriptionService) {
        super(categoryDescriptionService);
        this.categoryDescriptionService = categoryDescriptionService;
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