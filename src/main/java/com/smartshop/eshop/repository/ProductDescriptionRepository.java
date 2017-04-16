package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductDescription;

/**
 * Spring Data JPA repository for the ProductDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {

}
