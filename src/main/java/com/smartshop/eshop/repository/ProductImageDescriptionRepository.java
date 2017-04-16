package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductImageDescription;

/**
 * Spring Data JPA repository for the ProductImageDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductImageDescriptionRepository extends JpaRepository<ProductImageDescription, Long> {

}
