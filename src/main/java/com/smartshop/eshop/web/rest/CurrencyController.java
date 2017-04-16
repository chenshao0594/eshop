package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.service.CurrencyService;

/**
 * REST controller for managing Currency.
 */
@RestController
@RequestMapping("/api/"+ CurrencyController.SECTION_KEY)
public class CurrencyController extends AbstractDomainController< Currency, Long>{

    private final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    public static final String SECTION_KEY = "currencies";
    private static final String ENTITY_NAME = "currency";
        
     private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        super(currencyService);
        this.currencyService = currencyService;
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