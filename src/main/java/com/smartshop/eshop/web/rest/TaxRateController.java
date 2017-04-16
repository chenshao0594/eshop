package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.TaxRate;
import com.smartshop.eshop.service.TaxRateService;

/**
 * REST controller for managing TaxRate.
 */
@RestController
@RequestMapping("/api/"+ TaxRateController.SECTION_KEY)
public class TaxRateController extends AbstractDomainController< TaxRate, Long>{

    private final Logger log = LoggerFactory.getLogger(TaxRateController.class);
    public static final String SECTION_KEY = "tax-rates";
    private static final String ENTITY_NAME = "taxRate";
        
     private final TaxRateService taxRateService;

    public TaxRateController(TaxRateService taxRateService) {
        super(taxRateService);
        this.taxRateService = taxRateService;
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