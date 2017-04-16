package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductOption;

/**
 * Spring Data JPA repository for the ProductOption entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {

}
