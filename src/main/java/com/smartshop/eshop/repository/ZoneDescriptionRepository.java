package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ZoneDescription;

/**
 * Spring Data JPA repository for the ZoneDescription entity.
 */
@SuppressWarnings("unused")
public interface ZoneDescriptionRepository extends JpaRepository<ZoneDescription, Long> {

}
