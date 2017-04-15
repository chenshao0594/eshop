package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.GeoZone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GeoZone entity.
 */
@SuppressWarnings("unused")
public interface GeoZoneRepository extends JpaRepository<GeoZone,Long> {

}
