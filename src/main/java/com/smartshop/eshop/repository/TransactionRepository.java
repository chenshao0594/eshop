package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Transaction;

/**
 * Spring Data JPA repository for the Transaction entity.
 */
@SuppressWarnings("unused")
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
