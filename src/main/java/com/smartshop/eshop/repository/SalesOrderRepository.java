package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.SalesOrder;

/**
 * Spring Data JPA repository for the SalesOrder entity.
 */
@SuppressWarnings("unused")
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {

}
