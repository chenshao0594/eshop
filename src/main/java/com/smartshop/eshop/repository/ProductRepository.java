package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
