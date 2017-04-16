package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Template;

/**
 * Spring Data JPA repository for the Template entity.
 */
@SuppressWarnings("unused")
public interface TemplateRepository extends JpaRepository<Template,Long> {

}
