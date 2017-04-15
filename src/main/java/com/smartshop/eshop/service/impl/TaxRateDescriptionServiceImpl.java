package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.TaxRateDescriptionService;
import com.smartshop.eshop.domain.TaxRateDescription;
import com.smartshop.eshop.repository.TaxRateDescriptionRepository;
import com.smartshop.eshop.repository.search.TaxRateDescriptionSearchRepository;
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
 * Service Implementation for managing TaxRateDescription.
 */
@Service
@Transactional
public class TaxRateDescriptionServiceImpl extends AbstractDomainServiceImpl< TaxRateDescription, Long> implements TaxRateDescriptionService{

    private final Logger LOGGER = LoggerFactory.getLogger(TaxRateDescriptionServiceImpl.class);
    private final TaxRateDescriptionRepository taxRateDescriptionRepository;
    private final TaxRateDescriptionSearchRepository taxRateDescriptionSearchRepository;
    
    public TaxRateDescriptionServiceImpl(TaxRateDescriptionRepository taxRateDescriptionRepository, TaxRateDescriptionSearchRepository taxRateDescriptionSearchRepository) {
        super(taxRateDescriptionRepository,taxRateDescriptionSearchRepository);
        this.taxRateDescriptionRepository = taxRateDescriptionRepository;
        this.taxRateDescriptionSearchRepository = taxRateDescriptionSearchRepository;
    }
    
}
