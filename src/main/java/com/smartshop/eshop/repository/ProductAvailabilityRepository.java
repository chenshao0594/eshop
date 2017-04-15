package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductAvailability;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductAvailability entity.
 */
@SuppressWarnings("unused")
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability,Long> {

}
