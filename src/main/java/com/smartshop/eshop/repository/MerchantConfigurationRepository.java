package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.MerchantConfiguration;

/**
 * Spring Data JPA repository for the MerchantConfiguration entity.
 */
@SuppressWarnings("unused")
public interface MerchantConfigurationRepository extends JpaRepository<MerchantConfiguration,Long> {

}
