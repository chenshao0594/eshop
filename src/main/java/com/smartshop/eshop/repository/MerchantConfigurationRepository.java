package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.MerchantConfiguration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MerchantConfiguration entity.
 */
@SuppressWarnings("unused")
public interface MerchantConfigurationRepository extends JpaRepository<MerchantConfiguration,Long> {

}
