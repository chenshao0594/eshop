package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.IntegrationModule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IntegrationModule entity.
 */
@SuppressWarnings("unused")
public interface IntegrationModuleRepository extends JpaRepository<IntegrationModule,Long> {

}
