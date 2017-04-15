package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Manufacturer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Manufacturer entity.
 */
@SuppressWarnings("unused")
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

}
