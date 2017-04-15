package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.Content;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends JpaRepository<Content,Long> {

}
