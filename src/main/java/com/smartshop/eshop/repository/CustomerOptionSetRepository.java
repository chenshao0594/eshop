package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOptionSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOptionSet entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionSetRepository extends JpaRepository<CustomerOptionSet,Long> {

}
