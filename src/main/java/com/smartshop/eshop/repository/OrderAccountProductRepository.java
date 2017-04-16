package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderAccountProduct;

/**
 * Spring Data JPA repository for the OrderAccountProduct entity.
 */
@SuppressWarnings("unused")
public interface OrderAccountProductRepository extends JpaRepository<OrderAccountProduct, Long> {

}
