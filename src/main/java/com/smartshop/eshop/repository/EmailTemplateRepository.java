package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.EmailTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmailTemplate entity.
 */
@SuppressWarnings("unused")
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate,Long> {

}
