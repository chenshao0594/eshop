package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.DigitalProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DigitalProduct entity.
 */
@SuppressWarnings("unused")
public interface DigitalProductRepository extends JpaRepository<DigitalProduct,Long> {

}
