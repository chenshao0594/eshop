package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CountryDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CountryDescription entity.
 */
@SuppressWarnings("unused")
public interface CountryDescriptionRepository extends JpaRepository<CountryDescription,Long> {

}
