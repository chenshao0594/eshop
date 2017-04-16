package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.TaxRateDescription;

/**
 * Spring Data JPA repository for the TaxRateDescription entity.
 */
@SuppressWarnings("unused")
public interface TaxRateDescriptionRepository extends JpaRepository<TaxRateDescription,Long> {

}
