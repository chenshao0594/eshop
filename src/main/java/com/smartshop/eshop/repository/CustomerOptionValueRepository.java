package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOptionValue;

/**
 * Spring Data JPA repository for the CustomerOptionValue entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionValueRepository extends JpaRepository<CustomerOptionValue,Long> {

}
