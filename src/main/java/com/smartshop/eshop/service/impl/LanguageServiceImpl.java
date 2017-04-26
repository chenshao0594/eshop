package com.smartshop.eshop.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.common.BusinessConstants;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.exception.BusinessException;
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

	@Override
	public Language getByCode(String code) throws BusinessException {
		return languageRepository.findByCode(code);
	}

	@Override
	public Locale toLocale(Language language) {
		return new Locale(language.getCode());
	}

	@Override
	public Language toLanguage(Locale locale) {

		try {
			Language lang = getLanguagesMap().get(locale.getLanguage());
			return lang;
		} catch (Exception e) {
			LOGGER.error("Cannot convert locale " + locale.getLanguage() + " to language");
		}

		return new Language(BusinessConstants.DEFAULT_LANGUAGE);

	}

	@Override
	public Map<String, Language> getLanguagesMap() throws BusinessException {
		List<Language> langs = this.getLanguages();
		Map<String, Language> returnMap = new LinkedHashMap<String, Language>();
		for (Language lang : langs) {
			returnMap.put(lang.getCode(), lang);
		}
		return returnMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Language> getLanguages() throws BusinessException {

		List<Language> langs = null;
		try {
			langs = this.list();
		} catch (Exception e) {
			LOGGER.error("getCountries()", e);
			throw new BusinessException(e);
		}
		return langs;
	}

	@Override
	public Language defaultLanguage() {
		return toLanguage(Locale.ENGLISH);
	}
}
