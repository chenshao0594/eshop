package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.repository.TaxClassRepository;
import com.smartshop.eshop.repository.search.TaxClassSearchRepository;
import com.smartshop.eshop.service.TaxClassService;

/**
 * Service Implementation for managing TaxClass.
 */
@Service
@Transactional
public class TaxClassServiceImpl extends AbstractDomainServiceImpl< TaxClass, Long> implements TaxClassService{

    private final Logger LOGGER = LoggerFactory.getLogger(TaxClassServiceImpl.class);
    private final TaxClassRepository taxClassRepository;
    private final TaxClassSearchRepository taxClassSearchRepository;
    
    public TaxClassServiceImpl(TaxClassRepository taxClassRepository, TaxClassSearchRepository taxClassSearchRepository) {
        super(taxClassRepository,taxClassSearchRepository);
        this.taxClassRepository = taxClassRepository;
        this.taxClassSearchRepository = taxClassSearchRepository;
    }
    
}
