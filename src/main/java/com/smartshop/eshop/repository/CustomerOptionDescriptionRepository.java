package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOptionDescription;

/**
 * Spring Data JPA repository for the CustomerOptionDescription entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionDescriptionRepository extends JpaRepository<CustomerOptionDescription,Long> {

}
