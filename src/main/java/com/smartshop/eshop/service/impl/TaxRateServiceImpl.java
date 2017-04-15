package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.TaxRateService;
import com.smartshop.eshop.domain.TaxRate;
import com.smartshop.eshop.repository.TaxRateRepository;
import com.smartshop.eshop.repository.search.TaxRateSearchRepository;
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
 * Service Implementation for managing TaxRate.
 */
@Service
@Transactional
public class TaxRateServiceImpl extends AbstractDomainServiceImpl< TaxRate, Long> implements TaxRateService{

    private final Logger LOGGER = LoggerFactory.getLogger(TaxRateServiceImpl.class);
    private final TaxRateRepository taxRateRepository;
    private final TaxRateSearchRepository taxRateSearchRepository;
    
    public TaxRateServiceImpl(TaxRateRepository taxRateRepository, TaxRateSearchRepository taxRateSearchRepository) {
        super(taxRateRepository,taxRateSearchRepository);
        this.taxRateRepository = taxRateRepository;
        this.taxRateSearchRepository = taxRateSearchRepository;
    }
    
}
