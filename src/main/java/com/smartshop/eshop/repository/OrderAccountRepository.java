package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderAccount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderAccount entity.
 */
@SuppressWarnings("unused")
public interface OrderAccountRepository extends JpaRepository<OrderAccount,Long> {

}
