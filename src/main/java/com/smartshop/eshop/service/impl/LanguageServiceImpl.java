package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.repository.LanguageRepository;
import com.smartshop.eshop.repository.search.LanguageSearchRepository;
import com.smartshop.eshop.service.LanguageService;

/**
 * Service Implementation for managing Language.
 */
@Service
@Transactional
public class LanguageServiceImpl extends AbstractDomainServiceImpl<Language, Long> implements LanguageService {

	private final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);
	private final LanguageRepository languageRepository;
	private final LanguageSearchRepository languageSearchRepository;

	public LanguageServiceImpl(LanguageRepository languageRepository,
			LanguageSearchRepository languageSearchRepository) {
		super(languageRepository, languageSearchRepository);
		this.languageRepository = languageRepository;
		this.languageSearchRepository = languageSearchRepository;
	}

}
