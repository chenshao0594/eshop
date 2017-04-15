package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ShippingOrigin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShippingOrigin entity.
 */
@SuppressWarnings("unused")
public interface ShippingOriginRepository extends JpaRepository<ShippingOrigin,Long> {

}
