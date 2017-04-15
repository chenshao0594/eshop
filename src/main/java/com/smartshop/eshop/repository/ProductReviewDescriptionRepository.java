package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductReviewDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductReviewDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductReviewDescriptionRepository extends JpaRepository<ProductReviewDescription,Long> {

}
