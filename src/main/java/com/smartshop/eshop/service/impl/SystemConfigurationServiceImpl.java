package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.SystemConfiguration;
import com.smartshop.eshop.repository.SystemConfigurationRepository;
import com.smartshop.eshop.repository.search.SystemConfigurationSearchRepository;
import com.smartshop.eshop.service.SystemConfigurationService;

/**
 * Service Implementation for managing SystemConfiguration.
 */
@Service
@Transactional
public class SystemConfigurationServiceImpl extends AbstractDomainServiceImpl< SystemConfiguration, Long> implements SystemConfigurationService{

    private final Logger LOGGER = LoggerFactory.getLogger(SystemConfigurationServiceImpl.class);
    private final SystemConfigurationRepository systemConfigurationRepository;
    private final SystemConfigurationSearchRepository systemConfigurationSearchRepository;
    
    public SystemConfigurationServiceImpl(SystemConfigurationRepository systemConfigurationRepository, SystemConfigurationSearchRepository systemConfigurationSearchRepository) {
        super(systemConfigurationRepository,systemConfigurationSearchRepository);
        this.systemConfigurationRepository = systemConfigurationRepository;
        this.systemConfigurationSearchRepository = systemConfigurationSearchRepository;
    }
    
}
