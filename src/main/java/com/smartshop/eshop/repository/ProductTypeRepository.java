package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductType;

/**
 * Spring Data JPA repository for the ProductType entity.
 */
@SuppressWarnings("unused")
public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {

}
