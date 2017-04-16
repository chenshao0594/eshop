package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Country;

/**
 * Spring Data JPA repository for the Country entity.
 */
@SuppressWarnings("unused")
public interface CountryRepository extends JpaRepository<Country,Long> {

}
