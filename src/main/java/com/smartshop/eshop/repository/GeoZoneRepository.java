package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.GeoZone;

/**
 * Spring Data JPA repository for the GeoZone entity.
 */
@SuppressWarnings("unused")
public interface GeoZoneRepository extends JpaRepository<GeoZone, Long> {

}
