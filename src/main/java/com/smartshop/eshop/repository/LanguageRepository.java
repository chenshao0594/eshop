package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.exception.BusinessException;

public interface LanguageRepository extends JpaRepository <Language, Long> {
	Language findByCode(String code) throws BusinessException;
}
