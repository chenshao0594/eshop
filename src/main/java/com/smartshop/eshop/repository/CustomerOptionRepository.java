package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOption;

/**
 * Spring Data JPA repository for the CustomerOption entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionRepository extends JpaRepository<CustomerOption,Long> {

}
