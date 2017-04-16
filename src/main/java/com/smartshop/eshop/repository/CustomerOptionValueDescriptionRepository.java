package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;

/**
 * Spring Data JPA repository for the CustomerOptionValueDescription entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionValueDescriptionRepository extends JpaRepository<CustomerOptionValueDescription, Long> {

}
