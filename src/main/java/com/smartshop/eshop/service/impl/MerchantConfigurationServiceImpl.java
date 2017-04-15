package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.MerchantConfigurationService;
import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.repository.MerchantConfigurationRepository;
import com.smartshop.eshop.repository.search.MerchantConfigurationSearchRepository;
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
 * Service Implementation for managing MerchantConfiguration.
 */
@Service
@Transactional
public class MerchantConfigurationServiceImpl extends AbstractDomainServiceImpl< MerchantConfiguration, Long> implements MerchantConfigurationService{

    private final Logger LOGGER = LoggerFactory.getLogger(MerchantConfigurationServiceImpl.class);
    private final MerchantConfigurationRepository merchantConfigurationRepository;
    private final MerchantConfigurationSearchRepository merchantConfigurationSearchRepository;
    
    public MerchantConfigurationServiceImpl(MerchantConfigurationRepository merchantConfigurationRepository, MerchantConfigurationSearchRepository merchantConfigurationSearchRepository) {
        super(merchantConfigurationRepository,merchantConfigurationSearchRepository);
        this.merchantConfigurationRepository = merchantConfigurationRepository;
        this.merchantConfigurationSearchRepository = merchantConfigurationSearchRepository;
    }
    
}
