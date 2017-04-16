package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.MerchantLog;

/**
 * Spring Data JPA repository for the MerchantLog entity.
 */
@SuppressWarnings("unused")
public interface MerchantLogRepository extends JpaRepository<MerchantLog,Long> {

}
