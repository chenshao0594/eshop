package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.SystemConfiguration;

/**
 * Spring Data JPA repository for the SystemConfiguration entity.
 */
@SuppressWarnings("unused")
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration,Long> {

}
