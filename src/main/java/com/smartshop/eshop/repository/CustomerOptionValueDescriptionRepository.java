package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerOptionValueDescription entity.
 */
@SuppressWarnings("unused")
public interface CustomerOptionValueDescriptionRepository extends JpaRepository<CustomerOptionValueDescription,Long> {

}
