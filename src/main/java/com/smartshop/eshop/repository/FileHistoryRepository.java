package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.FileHistory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the FileHistory entity.
 */
@SuppressWarnings("unused")
public interface FileHistoryRepository extends JpaRepository<FileHistory,Long> {

}
