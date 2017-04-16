package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerAttribute;

/**
 * Spring Data JPA repository for the CustomerAttribute entity.
 */
@SuppressWarnings("unused")
public interface CustomerAttributeRepository extends JpaRepository<CustomerAttribute,Long> {

}
