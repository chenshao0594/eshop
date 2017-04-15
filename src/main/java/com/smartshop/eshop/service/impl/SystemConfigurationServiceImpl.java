package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.SystemConfigurationService;
import com.smartshop.eshop.domain.SystemConfiguration;
import com.smartshop.eshop.repository.SystemConfigurationRepository;
import com.smartshop.eshop.repository.search.SystemConfigurationSearchRepository;
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
