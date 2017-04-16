package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.repository.MerchantStoreRepository;
import com.smartshop.eshop.repository.search.MerchantStoreSearchRepository;
import com.smartshop.eshop.service.MerchantStoreService;

/**
 * Service Implementation for managing MerchantStore.
 */
@Service
@Transactional
public class MerchantStoreServiceImpl extends AbstractDomainServiceImpl< MerchantStore, Long> implements MerchantStoreService{

    private final Logger LOGGER = LoggerFactory.getLogger(MerchantStoreServiceImpl.class);
    private final MerchantStoreRepository merchantStoreRepository;
    private final MerchantStoreSearchRepository merchantStoreSearchRepository;
    
    public MerchantStoreServiceImpl(MerchantStoreRepository merchantStoreRepository, MerchantStoreSearchRepository merchantStoreSearchRepository) {
        super(merchantStoreRepository,merchantStoreSearchRepository);
        this.merchantStoreRepository = merchantStoreRepository;
        this.merchantStoreSearchRepository = merchantStoreSearchRepository;
    }
    
}
