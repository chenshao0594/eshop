package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.OrderProductDownload;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderProductDownload entity.
 */
@SuppressWarnings("unused")
public interface OrderProductDownloadRepository extends JpaRepository<OrderProductDownload,Long> {

}
