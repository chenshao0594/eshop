package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderProduct;

/**
 * Spring Data JPA repository for the OrderProduct entity.
 */
@SuppressWarnings("unused")
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

}
