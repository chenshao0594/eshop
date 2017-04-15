package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.OptinService;
import com.smartshop.eshop.domain.Optin;
import com.smartshop.eshop.repository.OptinRepository;
import com.smartshop.eshop.repository.search.OptinSearchRepository;
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
