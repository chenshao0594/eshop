package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.ManufacturerDescriptionService;
import com.smartshop.eshop.domain.ManufacturerDescription;
import com.smartshop.eshop.repository.ManufacturerDescriptionRepository;
import com.smartshop.eshop.repository.search.ManufacturerDescriptionSearchRepository;
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
 * Service Implementation for managing ManufacturerDescription.
 */
@Service
@Transactional
public class ManufacturerDescriptionServiceImpl extends AbstractDomainServiceImpl< ManufacturerDescription, Long> implements ManufacturerDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(ManufacturerDescriptionServiceImpl.class);
    private final ManufacturerDescriptionRepository manufacturerDescriptionRepository;
    private final ManufacturerDescriptionSearchRepository manufacturerDescriptionSearchRepository;
    
    public ManufacturerDescriptionServiceImpl(ManufacturerDescriptionRepository manufacturerDescriptionRepository, ManufacturerDescriptionSearchRepository manufacturerDescriptionSearchRepository) {
        super(manufacturerDescriptionRepository,manufacturerDescriptionSearchRepository);
        this.manufacturerDescriptionRepository = manufacturerDescriptionRepository;
        this.manufacturerDescriptionSearchRepository = manufacturerDescriptionSearchRepository;
    }
    
}
