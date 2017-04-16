package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductImage;

/**
 * Spring Data JPA repository for the ProductImage entity.
 */
@SuppressWarnings("unused")
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

}
