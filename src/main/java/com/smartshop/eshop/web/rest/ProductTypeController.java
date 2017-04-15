package com.smartshop.eshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.ProductType;
import com.smartshop.eshop.service.ProductTypeService;
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
 * REST controller for managing ProductType.
 */
@RestController
@RequestMapping("/api/"+ ProductTypeController.SECTION_KEY)
public class ProductTypeController extends AbstractDomainController< ProductType, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductTypeController.class);
    public static final String SECTION_KEY = "product-types";
    private static final String ENTITY_NAME = "productType";
        
     private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        super(productTypeService);
        this.productTypeService = productTypeService;
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