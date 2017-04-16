package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;

/**
 * Spring Data JPA repository for the ShoppingCartAttributeItem entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartAttributeItemRepository extends JpaRepository<ShoppingCartAttributeItem,Long> {

}
