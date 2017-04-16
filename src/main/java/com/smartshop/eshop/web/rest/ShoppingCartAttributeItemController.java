package com.smartshop.eshop.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import com.smartshop.eshop.service.ShoppingCartAttributeItemService;
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
 * REST controller for managing ShoppingCartAttributeItem.
 */
@RestController
@RequestMapping("/api/"+ ShoppingCartAttributeItemController.SECTION_KEY)
public class ShoppingCartAttributeItemController extends AbstractDomainController< ShoppingCartAttributeItem, Long>{

    private final Logger log = LoggerFactory.getLogger(ShoppingCartAttributeItemController.class);
    public static final String SECTION_KEY = "shopping-cart-attribute-items";
    private static final String ENTITY_NAME = "shoppingCartAttributeItem";
        
     private final ShoppingCartAttributeItemService shoppingCartAttributeItemService;

    public ShoppingCartAttributeItemController(ShoppingCartAttributeItemService shoppingCartAttributeItemService) {
        super(shoppingCartAttributeItemService);
        this.shoppingCartAttributeItemService = shoppingCartAttributeItemService;
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