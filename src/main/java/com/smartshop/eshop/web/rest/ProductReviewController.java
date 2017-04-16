package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.ProductReview;
import com.smartshop.eshop.service.ProductReviewService;

/**
 * REST controller for managing ProductReview.
 */
@RestController
@RequestMapping("/api/"+ ProductReviewController.SECTION_KEY)
public class ProductReviewController extends AbstractDomainController< ProductReview, Long>{

    private final Logger log = LoggerFactory.getLogger(ProductReviewController.class);
    public static final String SECTION_KEY = "product-reviews";
    private static final String ENTITY_NAME = "productReview";
        
     private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        super(productReviewService);
        this.productReviewService = productReviewService;
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