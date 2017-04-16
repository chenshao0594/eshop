package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductRelationship;

public interface ProductRelationshipRepository
		extends JpaRepository<ProductRelationship, Long>, ProductRelationshipRepositoryCustom {

}
