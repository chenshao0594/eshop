package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CountryDescription;
import com.smartshop.eshop.repository.CountryDescriptionRepository;
import com.smartshop.eshop.repository.search.CountryDescriptionSearchRepository;
import com.smartshop.eshop.service.CountryDescriptionService;

/**
 * Service Implementation for managing CountryDescription.
 */
@Service
@Transactional
public class CountryDescriptionServiceImpl extends AbstractDomainServiceImpl< CountryDescription, Long> implements CountryDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(CountryDescriptionServiceImpl.class);
    private final CountryDescriptionRepository countryDescriptionRepository;
    private final CountryDescriptionSearchRepository countryDescriptionSearchRepository;
    
    public CountryDescriptionServiceImpl(CountryDescriptionRepository countryDescriptionRepository, CountryDescriptionSearchRepository countryDescriptionSearchRepository) {
        super(countryDescriptionRepository,countryDescriptionSearchRepository);
        this.countryDescriptionRepository = countryDescriptionRepository;
        this.countryDescriptionSearchRepository = countryDescriptionSearchRepository;
    }
    
}
