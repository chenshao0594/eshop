package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.service.MerchantStoreService;

/**
 * REST controller for managing MerchantStore.
 */
@RestController
@RequestMapping("/api/"+ MerchantStoreController.SECTION_KEY)
public class MerchantStoreController extends AbstractDomainController< MerchantStore, Long>{

    private final Logger log = LoggerFactory.getLogger(MerchantStoreController.class);
    public static final String SECTION_KEY = "merchant-stores";
    private static final String ENTITY_NAME = "merchantStore";
        
     private final MerchantStoreService merchantStoreService;

    public MerchantStoreController(MerchantStoreService merchantStoreService) {
        super(merchantStoreService);
        this.merchantStoreService = merchantStoreService;
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