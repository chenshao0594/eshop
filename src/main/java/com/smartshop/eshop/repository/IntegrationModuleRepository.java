package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.IntegrationModule;

/**
 * Spring Data JPA repository for the IntegrationModule entity.
 */
@SuppressWarnings("unused")
public interface IntegrationModuleRepository extends JpaRepository<IntegrationModule,Long> {

}
