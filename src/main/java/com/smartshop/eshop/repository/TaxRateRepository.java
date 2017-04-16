package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.TaxRate;

/**
 * Spring Data JPA repository for the TaxRate entity.
 */
@SuppressWarnings("unused")
public interface TaxRateRepository extends JpaRepository<TaxRate,Long> {

}
