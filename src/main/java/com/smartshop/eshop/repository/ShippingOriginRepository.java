package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShippingOrigin;

/**
 * Spring Data JPA repository for the ShippingOrigin entity.
 */
@SuppressWarnings("unused")
public interface ShippingOriginRepository extends JpaRepository<ShippingOrigin,Long> {

}
