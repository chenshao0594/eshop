package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOptionSet;

/**
 * Spring Data JPA repository for the CustomerOptionSet entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionSetRepository extends JpaRepository<CustomerOptionSet,Long> {

}
