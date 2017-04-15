package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ManufacturerDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ManufacturerDescription entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerDescriptionRepository extends JpaRepository<ManufacturerDescription,Long> {

}
