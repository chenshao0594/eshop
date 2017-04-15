package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductOptionValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionValue entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue,Long> {

}
