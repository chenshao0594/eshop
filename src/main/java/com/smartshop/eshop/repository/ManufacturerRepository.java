package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Manufacturer;

/**
 * Spring Data JPA repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

}
