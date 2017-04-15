package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOptin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOptin entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptinRepository extends JpaRepository<CustomerOptin,Long> {

}
