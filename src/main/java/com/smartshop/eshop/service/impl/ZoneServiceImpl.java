package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ZoneService;
import com.smartshop.eshop.domain.Zone;
import com.smartshop.eshop.repository.ZoneRepository;
import com.smartshop.eshop.repository.search.ZoneSearchRepository;
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
 * Service Implementation for managing Zone.
 */
@Service
@Transactional
public class ZoneServiceImpl extends AbstractDomainServiceImpl< Zone, Long> implements ZoneService{

    private final Logger LOGGER = LoggerFactory.getLogger(ZoneServiceImpl.class);
    private final ZoneRepository zoneRepository;
    private final ZoneSearchRepository zoneSearchRepository;
    
    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneSearchRepository zoneSearchRepository) {
        super(zoneRepository,zoneSearchRepository);
        this.zoneRepository = zoneRepository;
        this.zoneSearchRepository = zoneSearchRepository;
    }
    
}
