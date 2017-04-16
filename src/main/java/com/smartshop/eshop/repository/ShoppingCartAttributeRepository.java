package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
public interface ShoppingCartAttributeRepository extends JpaRepository<ShoppingCartAttributeItem, Long> {


}
