package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartshop.eshop.domain.SalesOrder;

public interface OrderRepository extends JpaRepository<SalesOrder, Long>, SalesOrderRepositoryCustom {

    @Query("select o from Order o join fetch o.orderProducts op join fetch o.orderTotal ot left join fetch o.orderHistory oh left join fetch op.downloads opd left join fetch op.orderAttributes opa left join fetch op.prices opp where o.id = ?1")
	SalesOrder findOne(Long id);
}
