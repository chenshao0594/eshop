package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderAccount;

public interface OrderAccountRepository extends JpaRepository<OrderAccount, Long> {


}
