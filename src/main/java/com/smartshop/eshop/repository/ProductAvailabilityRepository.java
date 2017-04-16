package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductAvailability;

/**
 * Spring Data JPA repository for the ProductAvailability entity.
 */
@SuppressWarnings("unused")
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability,Long> {

}
