package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.MerchantStore;

/**
 * Spring Data JPA repository for the MerchantStore entity.
 */
@SuppressWarnings("unused")
public interface MerchantStoreRepository extends JpaRepository<MerchantStore, Long> {

}
