package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.TaxClass;

/**
 * Spring Data JPA repository for the TaxClass entity.
 */
@SuppressWarnings("unused")
public interface TaxClassRepository extends JpaRepository<TaxClass,Long> {

}
