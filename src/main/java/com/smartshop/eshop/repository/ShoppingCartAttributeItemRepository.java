package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ShoppingCartAttributeItem entity.
 */
@SuppressWarnings("unused")
public interface ShoppingCartAttributeItemRepository extends JpaRepository<ShoppingCartAttributeItem,Long> {

}
