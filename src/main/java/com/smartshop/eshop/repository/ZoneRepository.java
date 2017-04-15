package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Zone;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Zone entity.
 */
@SuppressWarnings("unused")
public interface ZoneRepository extends JpaRepository<Zone,Long> {

}
