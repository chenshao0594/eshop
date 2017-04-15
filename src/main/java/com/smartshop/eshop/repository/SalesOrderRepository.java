package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.SalesOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SalesOrder entity.
 */
@SuppressWarnings("unused")
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {

}
