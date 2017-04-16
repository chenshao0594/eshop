package com.smartshop.eshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.MerchantLog;
import com.smartshop.eshop.domain.MerchantStore;

public interface MerchantLogRepository extends JpaRepository<MerchantLog, Long> {

	public List<MerchantLog> findByStore(MerchantStore store);
}
