package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CustomerOptin;

/**
 * Spring Data JPA repository for the CustomerOptin entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptinRepository extends JpaRepository<CustomerOptin,Long> {

}
