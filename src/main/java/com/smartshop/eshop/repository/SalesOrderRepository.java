package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.temp.SalesOrderCriteria;
import com.smartshop.eshop.temp.SalesOrderList;

/**
 * Spring Data JPA repository for the SalesOrder entity.
 */
@SuppressWarnings("unused")
public interface SalesOrderRepository extends JpaRepository<SalesOrder,Long> {
	SalesOrderList listByStore(MerchantStore store, SalesOrderCriteria criteria);

}
