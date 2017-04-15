package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOption entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionRepository extends JpaRepository<CustomerOption,Long> {

}
