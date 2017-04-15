package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ZoneDescriptionService;
import com.smartshop.eshop.domain.ZoneDescription;
import com.smartshop.eshop.repository.ZoneDescriptionRepository;
import com.smartshop.eshop.repository.search.ZoneDescriptionSearchRepository;
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
 * Service Implementation for managing ZoneDescription.
 */
@Service
@Transactional
public class ZoneDescriptionServiceImpl extends AbstractDomainServiceImpl< ZoneDescription, Long> implements ZoneDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ZoneDescriptionServiceImpl.class);
    private final ZoneDescriptionRepository zoneDescriptionRepository;
    private final ZoneDescriptionSearchRepository zoneDescriptionSearchRepository;
    
    public ZoneDescriptionServiceImpl(ZoneDescriptionRepository zoneDescriptionRepository, ZoneDescriptionSearchRepository zoneDescriptionSearchRepository) {
        super(zoneDescriptionRepository,zoneDescriptionSearchRepository);
        this.zoneDescriptionRepository = zoneDescriptionRepository;
        this.zoneDescriptionSearchRepository = zoneDescriptionSearchRepository;
    }
    
}
