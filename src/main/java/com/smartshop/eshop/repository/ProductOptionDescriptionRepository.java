package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductOptionDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionDescriptionRepository extends JpaRepository<ProductOptionDescription,Long> {

}
