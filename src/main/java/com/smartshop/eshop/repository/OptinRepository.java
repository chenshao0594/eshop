package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Optin;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Optin entity.
 */
@SuppressWarnings("unused")
public interface OptinRepository extends JpaRepository<Optin,Long> {

}
