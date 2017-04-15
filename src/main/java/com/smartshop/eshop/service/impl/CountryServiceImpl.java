package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CountryService;
import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.repository.CountryRepository;
import com.smartshop.eshop.repository.search.CountrySearchRepository;
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
 * Service Implementation for managing Country.
 */
@Service
@Transactional
public class CountryServiceImpl extends AbstractDomainServiceImpl< Country, Long> implements CountryService{

    private final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    private final CountryRepository countryRepository;
    private final CountrySearchRepository countrySearchRepository;
    
    public CountryServiceImpl(CountryRepository countryRepository, CountrySearchRepository countrySearchRepository) {
        super(countryRepository,countrySearchRepository);
        this.countryRepository = countryRepository;
        this.countrySearchRepository = countrySearchRepository;
    }
    
}
