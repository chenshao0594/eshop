package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.FileHistory;

/**
 * Spring Data JPA repository for the FileHistory entity.
 */
@SuppressWarnings("unused")
public interface FileHistoryRepository extends JpaRepository<FileHistory,Long> {

}
