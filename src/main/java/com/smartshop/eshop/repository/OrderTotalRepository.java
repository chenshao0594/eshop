package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderTotal;

/**
 * Spring Data JPA repository for the OrderTotal entity.
 */
@SuppressWarnings("unused")
public interface OrderTotalRepository extends JpaRepository<OrderTotal,Long> {

}
