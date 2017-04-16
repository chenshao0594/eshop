package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Currency;

/**
 * Spring Data JPA repository for the Currency entity.
 */
@SuppressWarnings("unused")
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

}
