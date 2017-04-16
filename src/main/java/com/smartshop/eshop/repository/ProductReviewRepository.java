package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductReview;

/**
 * Spring Data JPA repository for the ProductReview entity.
 */
@SuppressWarnings("unused")
public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {

}
