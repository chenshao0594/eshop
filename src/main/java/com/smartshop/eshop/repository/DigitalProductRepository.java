package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.DigitalProduct;

/**
 * Spring Data JPA repository for the DigitalProduct entity.
 */
@SuppressWarnings("unused")
public interface DigitalProductRepository extends JpaRepository<DigitalProduct,Long> {

}
