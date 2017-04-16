package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductPriceDescription;

/**
 * Spring Data JPA repository for the ProductPriceDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductPriceDescriptionRepository extends JpaRepository<ProductPriceDescription,Long> {

}
