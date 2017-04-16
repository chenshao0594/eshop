package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Content;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends JpaRepository<Content,Long> {

}
