package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderAccountProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderAccountProduct entity.
 */
@SuppressWarnings("unused")
public interface OrderAccountProductRepository extends JpaRepository<OrderAccountProduct,Long> {

}
