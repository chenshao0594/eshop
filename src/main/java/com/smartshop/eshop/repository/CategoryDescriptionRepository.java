package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.CategoryDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CategoryDescription entity.
 */
@SuppressWarnings("unused")
public interface CategoryDescriptionRepository extends JpaRepository<CategoryDescription,Long> {

}
