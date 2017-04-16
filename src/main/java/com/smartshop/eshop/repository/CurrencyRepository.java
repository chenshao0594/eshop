package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	Currency getByCode(String code);
}
