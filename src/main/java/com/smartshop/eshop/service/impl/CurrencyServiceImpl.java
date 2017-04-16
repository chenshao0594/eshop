package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.repository.CurrencyRepository;
import com.smartshop.eshop.repository.search.CurrencySearchRepository;
import com.smartshop.eshop.service.CurrencyService;

/**
 * Service Implementation for managing Currency.
 */
@Service
@Transactional
public class CurrencyServiceImpl extends AbstractDomainServiceImpl<Currency, Long> implements CurrencyService {

	private final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);
	private final CurrencyRepository currencyRepository;
	private final CurrencySearchRepository currencySearchRepository;

	public CurrencyServiceImpl(CurrencyRepository currencyRepository,
			CurrencySearchRepository currencySearchRepository) {
		super(currencyRepository, currencySearchRepository);
		this.currencyRepository = currencyRepository;
		this.currencySearchRepository = currencySearchRepository;
	}

}
