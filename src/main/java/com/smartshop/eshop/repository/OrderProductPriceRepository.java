package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderProductPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderProductPrice entity.
 */
@SuppressWarnings("unused")
public interface OrderProductPriceRepository extends JpaRepository<OrderProductPrice,Long> {

}
