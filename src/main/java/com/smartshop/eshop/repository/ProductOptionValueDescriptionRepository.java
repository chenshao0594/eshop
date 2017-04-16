package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductOptionValueDescription;

/**
 * Spring Data JPA repository for the ProductOptionValueDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueDescriptionRepository extends JpaRepository<ProductOptionValueDescription, Long> {

}
