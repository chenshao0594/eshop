package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderAccount;

/**
 * Spring Data JPA repository for the OrderAccount entity.
 */
@SuppressWarnings("unused")
public interface OrderAccountRepository extends JpaRepository<OrderAccount,Long> {

}
