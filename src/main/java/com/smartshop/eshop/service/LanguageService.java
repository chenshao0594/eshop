package com.smartshop.eshop.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing Language.
 */
public interface LanguageService extends AbstractDomainService<Language, Long> {
	Language getByCode(String code) throws BusinessException;

	Map<String, Language> getLanguagesMap() throws BusinessException;

	List<Language> getLanguages() throws BusinessException;

	Locale toLocale(Language language);

	Language toLanguage(Locale locale);

	Language defaultLanguage();
}