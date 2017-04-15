package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CurrencyService;
import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.repository.CurrencyRepository;
import com.smartshop.eshop.repository.search.CurrencySearchRepository;
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
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyServiceImpl extends AbstractDomainServiceImpl< Currency, Long> implements CurrencyService{

    private final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    private final CurrencyRepository currencyRepository;
    private final CurrencySearchRepository currencySearchRepository;
    
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencySearchRepository currencySearchRepository) {
        super(currencyRepository,currencySearchRepository);
        this.currencyRepository = currencyRepository;
        this.currencySearchRepository = currencySearchRepository;
    }
    
}
