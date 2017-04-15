package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderProductAttribute;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface OrderProductAttributeRepository extends JpaRepository<OrderProductAttribute,Long> {

}
