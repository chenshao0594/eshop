package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Zone;

/**
 * Spring Data JPA repository for the Zone entity.
 */
@SuppressWarnings("unused")
public interface ZoneRepository extends JpaRepository<Zone,Long> {

}
