package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderProductAttribute;

/**
 * Spring Data JPA repository for the OrderProductAttribute entity.
 */
@SuppressWarnings("unused")
public interface OrderProductAttributeRepository extends JpaRepository<OrderProductAttribute, Long> {

}
