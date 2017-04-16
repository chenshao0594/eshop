package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductPrice;

/**
 * Spring Data JPA repository for the ProductPrice entity.
 */
@SuppressWarnings("unused")
public interface ProductPriceRepository extends JpaRepository<ProductPrice,Long> {

}
