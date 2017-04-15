package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.TaxRate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaxRate entity.
 */
@SuppressWarnings("unused")
public interface TaxRateRepository extends JpaRepository<TaxRate,Long> {

}
