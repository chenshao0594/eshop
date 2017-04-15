package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductOptionValueDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOptionValueDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionValueDescriptionRepository extends JpaRepository<ProductOptionValueDescription,Long> {

}
