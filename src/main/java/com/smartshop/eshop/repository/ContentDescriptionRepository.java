package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ContentDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContentDescription entity.
 */
@SuppressWarnings("unused")
public interface ContentDescriptionRepository extends JpaRepository<ContentDescription,Long> {

}
