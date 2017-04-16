package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductRelationship;

/**
 * Spring Data JPA repository for the ProductRelationship entity.
 */
@SuppressWarnings("unused")
public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship,Long> {

}
