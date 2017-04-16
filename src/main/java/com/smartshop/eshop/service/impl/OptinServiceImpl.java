package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Optin;
import com.smartshop.eshop.repository.OptinRepository;
import com.smartshop.eshop.repository.search.OptinSearchRepository;
import com.smartshop.eshop.service.OptinService;

/**
 * Service Implementation for managing Optin.
 */
@Service
@Transactional
public class OptinServiceImpl extends AbstractDomainServiceImpl< Optin, Long> implements OptinService{

    private final Logger LOGGER = LoggerFactory.getLogger(OptinServiceImpl.class);
    private final OptinRepository optinRepository;
    private final OptinSearchRepository optinSearchRepository;
    
    public OptinServiceImpl(OptinRepository optinRepository, OptinSearchRepository optinSearchRepository) {
        super(optinRepository,optinSearchRepository);
        this.optinRepository = optinRepository;
        this.optinSearchRepository = optinSearchRepository;
    }
    
}
