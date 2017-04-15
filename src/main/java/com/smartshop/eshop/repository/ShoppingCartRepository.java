package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ShoppingCart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

}
