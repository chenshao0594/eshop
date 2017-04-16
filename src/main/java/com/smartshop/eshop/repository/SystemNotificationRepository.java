package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.SystemNotification;

/**
 * Spring Data JPA repository for the SystemNotification entity.
 */
@SuppressWarnings("unused")
public interface SystemNotificationRepository extends JpaRepository<SystemNotification,Long> {

}
