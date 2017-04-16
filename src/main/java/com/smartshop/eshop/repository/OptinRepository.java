package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.Optin;

/**
 * Spring Data JPA repository for the Optin entity.
 */
@SuppressWarnings("unused")
public interface OptinRepository extends JpaRepository<Optin,Long> {

}
