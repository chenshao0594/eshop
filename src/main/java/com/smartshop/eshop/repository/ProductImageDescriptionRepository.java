package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductImageDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductImageDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductImageDescriptionRepository extends JpaRepository<ProductImageDescription,Long> {

}
