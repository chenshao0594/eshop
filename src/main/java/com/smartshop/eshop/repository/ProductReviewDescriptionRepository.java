package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductReviewDescription;

/**
 * Spring Data JPA repository for the ProductReviewDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductReviewDescriptionRepository extends JpaRepository<ProductReviewDescription,Long> {

}
