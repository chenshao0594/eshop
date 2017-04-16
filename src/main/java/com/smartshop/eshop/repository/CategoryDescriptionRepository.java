package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.CategoryDescription;

/**
 * Spring Data JPA repository for the CategoryDescription entity.
 */
@SuppressWarnings("unused")
public interface CategoryDescriptionRepository extends JpaRepository<CategoryDescription,Long> {

}
