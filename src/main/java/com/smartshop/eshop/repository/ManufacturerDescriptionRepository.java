package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ManufacturerDescription;

/**
 * Spring Data JPA repository for the ManufacturerDescription entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerDescriptionRepository extends JpaRepository<ManufacturerDescription, Long> {

}
