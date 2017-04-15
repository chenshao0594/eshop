package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.SystemConfiguration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SystemConfiguration entity.
 */
@SuppressWarnings("unused")
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration,Long> {

}
