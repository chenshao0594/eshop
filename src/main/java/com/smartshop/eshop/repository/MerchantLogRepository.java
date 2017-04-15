package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.MerchantLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MerchantLog entity.
 */
@SuppressWarnings("unused")
public interface MerchantLogRepository extends JpaRepository<MerchantLog,Long> {

}
