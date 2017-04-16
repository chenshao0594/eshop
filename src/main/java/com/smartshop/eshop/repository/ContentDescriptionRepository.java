package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.ContentDescription;

/**
 * Spring Data JPA repository for the ContentDescription entity.
 */
@SuppressWarnings("unused")
public interface ContentDescriptionRepository extends JpaRepository<ContentDescription, Long> {

}
