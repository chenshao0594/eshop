package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderTotal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderTotal entity.
 */
@SuppressWarnings("unused")
public interface OrderTotalRepository extends JpaRepository<OrderTotal,Long> {

}
