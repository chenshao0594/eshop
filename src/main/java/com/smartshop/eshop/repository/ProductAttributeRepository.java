package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductAttribute;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {

}
