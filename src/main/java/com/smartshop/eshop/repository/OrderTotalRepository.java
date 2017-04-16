package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderTotal;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, Long> {

}
