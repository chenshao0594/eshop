package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShoppingCartItem;
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {


}
