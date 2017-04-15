package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.ZoneDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ZoneDescription entity.
 */
@SuppressWarnings("unused")
public interface ZoneDescriptionRepository extends JpaRepository<ZoneDescription,Long> {

}
