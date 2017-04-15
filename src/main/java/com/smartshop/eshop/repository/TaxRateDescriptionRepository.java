package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.TaxRateDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaxRateDescription entity.
 */
@SuppressWarnings("unused")
public interface TaxRateDescriptionRepository extends JpaRepository<TaxRateDescription,Long> {

}
