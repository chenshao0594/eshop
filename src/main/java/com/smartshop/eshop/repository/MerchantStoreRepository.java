package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.MerchantStore;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MerchantStore entity.
 */
@SuppressWarnings("unused")
public interface MerchantStoreRepository extends JpaRepository<MerchantStore,Long> {

}
