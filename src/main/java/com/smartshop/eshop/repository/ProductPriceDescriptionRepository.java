package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductPriceDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductPriceDescription entity.
 */
@SuppressWarnings("unused")
public interface ProductPriceDescriptionRepository extends JpaRepository<ProductPriceDescription,Long> {

}
