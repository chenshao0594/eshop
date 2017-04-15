package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOptionDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOptionDescription entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionDescriptionRepository extends JpaRepository<CustomerOptionDescription,Long> {

}
