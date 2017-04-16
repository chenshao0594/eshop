package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.GeoZoneDescription;

/**
 * Spring Data JPA repository for the GeoZoneDescription entity.
 */
@SuppressWarnings("unused")
public interface GeoZoneDescriptionRepository extends JpaRepository<GeoZoneDescription, Long> {

}
