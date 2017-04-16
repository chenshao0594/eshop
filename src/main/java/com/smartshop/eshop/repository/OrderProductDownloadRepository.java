package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.OrderProductDownload;

/**
 * Spring Data JPA repository for the OrderProductDownload entity.
 */
@SuppressWarnings("unused")
public interface OrderProductDownloadRepository extends JpaRepository<OrderProductDownload,Long> {

}
