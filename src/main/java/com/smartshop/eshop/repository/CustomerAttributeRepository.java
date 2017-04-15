package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CustomerAttribute;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerAttribute entity.
 */
@SuppressWarnings("unused")
public interface CustomerAttributeRepository extends JpaRepository<CustomerAttribute,Long> {

}
