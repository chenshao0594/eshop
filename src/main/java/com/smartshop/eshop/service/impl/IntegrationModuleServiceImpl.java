package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.IntegrationModuleService;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.repository.IntegrationModuleRepository;
import com.smartshop.eshop.repository.search.IntegrationModuleSearchRepository;
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
 * Service Implementation for managing IntegrationModule.
 */
@Service
@Transactional
public class IntegrationModuleServiceImpl extends AbstractDomainServiceImpl< IntegrationModule, Long> implements IntegrationModuleService{

    private final Logger LOGGER = LoggerFactory.getLogger(IntegrationModuleServiceImpl.class);
    private final IntegrationModuleRepository integrationModuleRepository;
    private final IntegrationModuleSearchRepository integrationModuleSearchRepository;
    
    public IntegrationModuleServiceImpl(IntegrationModuleRepository integrationModuleRepository, IntegrationModuleSearchRepository integrationModuleSearchRepository) {
        super(integrationModuleRepository,integrationModuleSearchRepository);
        this.integrationModuleRepository = integrationModuleRepository;
        this.integrationModuleSearchRepository = integrationModuleSearchRepository;
    }
    
}
