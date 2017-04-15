package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.TaxClass;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TaxClass entity.
 */
@SuppressWarnings("unused")
public interface TaxClassRepository extends JpaRepository<TaxClass,Long> {

}
