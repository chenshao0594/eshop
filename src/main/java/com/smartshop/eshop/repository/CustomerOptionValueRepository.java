package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOptionValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOptionValue entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionValueRepository extends JpaRepository<CustomerOptionValue,Long> {

}
