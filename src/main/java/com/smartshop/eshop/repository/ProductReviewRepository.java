package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductReview;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductReview entity.
 */
@SuppressWarnings("unused")
public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {

}
