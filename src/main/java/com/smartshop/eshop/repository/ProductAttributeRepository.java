package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductAttribute;

/**
 * Spring Data JPA repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

}
