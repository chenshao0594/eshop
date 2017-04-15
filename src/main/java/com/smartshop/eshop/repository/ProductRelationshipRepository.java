package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductRelationship;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductRelationship entity.
 */
@SuppressWarnings("unused")
public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship,Long> {

}
