package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderProductPrice;

/**
 * Spring Data JPA repository for the OrderProductPrice entity.
 */
@SuppressWarnings("unused")
public interface OrderProductPriceRepository extends JpaRepository<OrderProductPrice,Long> {

}
