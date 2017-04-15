package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderProduct entity.
 */
@SuppressWarnings("unused")
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {

}
