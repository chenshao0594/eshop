package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Customer;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
