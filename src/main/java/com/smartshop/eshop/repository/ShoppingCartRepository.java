package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShoppingCart;

/**
 * Spring Data JPA repository for the ShoppingCart entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

}
