package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
