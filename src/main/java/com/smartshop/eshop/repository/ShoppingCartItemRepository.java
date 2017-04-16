package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShoppingCartItem;

/**
 * Spring Data JPA repository for the ShoppingCartItem entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {

}
