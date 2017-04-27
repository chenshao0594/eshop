package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smartshop.eshop.domain.Attachment;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@SuppressWarnings("unused")
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, QueryDslPredicateExecutor<Attachment> {

}
