package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderStatusHistory;

/**
 * Spring Data JPA repository for the OrderStatusHistory entity.
 */
@SuppressWarnings("unused")
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory,Long> {

}
