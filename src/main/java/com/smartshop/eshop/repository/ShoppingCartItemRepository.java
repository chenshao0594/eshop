package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ShoppingCartItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCartItem entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {

}
