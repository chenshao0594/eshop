package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.MerchantStoreService;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.repository.MerchantStoreRepository;
import com.smartshop.eshop.repository.search.MerchantStoreSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

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
