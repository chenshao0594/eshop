package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CountryDescription;

/**
 * Spring Data JPA repository for the CountryDescription entity.
 */
@SuppressWarnings("unused")
public interface CountryDescriptionRepository extends JpaRepository<CountryDescription, Long> {

}
