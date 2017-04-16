package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductOptionDescription;

/**
 * Spring Data JPA repository for the ProductOptionDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionDescriptionRepository extends JpaRepository<ProductOptionDescription, Long> {

}
