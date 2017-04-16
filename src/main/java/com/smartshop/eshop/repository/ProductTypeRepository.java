package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	ProductType findByCode(String code);
}
