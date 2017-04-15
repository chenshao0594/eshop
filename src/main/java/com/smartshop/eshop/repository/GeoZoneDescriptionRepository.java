package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.GeoZoneDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GeoZoneDescription entity.
 */
@SuppressWarnings("unused")
public interface GeoZoneDescriptionRepository extends JpaRepository<GeoZoneDescription,Long> {

}
