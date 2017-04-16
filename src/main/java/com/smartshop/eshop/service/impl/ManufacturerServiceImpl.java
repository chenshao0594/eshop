package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Manufacturer;
import com.smartshop.eshop.repository.ManufacturerRepository;
import com.smartshop.eshop.repository.search.ManufacturerSearchRepository;
import com.smartshop.eshop.service.ManufacturerService;

/**
 * Service Implementation for managing Manufacturer.
 */
@Service
@Transactional
public class ManufacturerServiceImpl extends AbstractDomainServiceImpl< Manufacturer, Long> implements ManufacturerService{

    private final Logger LOGGER = LoggerFactory.getLogger(ManufacturerServiceImpl.class);
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerSearchRepository manufacturerSearchRepository;
    
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository, ManufacturerSearchRepository manufacturerSearchRepository) {
        super(manufacturerRepository,manufacturerSearchRepository);
        this.manufacturerRepository = manufacturerRepository;
        this.manufacturerSearchRepository = manufacturerSearchRepository;
    }
    
}
