package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ProductOption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProductOption entity.
 */
@SuppressWarnings("unused")
public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {

}
