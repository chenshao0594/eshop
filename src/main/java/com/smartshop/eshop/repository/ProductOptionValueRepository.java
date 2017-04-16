package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductOptionValue;

/**
 * Spring Data JPA repository for the ProductOptionValue entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue,Long> {

}
