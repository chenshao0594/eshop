package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.SystemNotification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SystemNotification entity.
 */
@SuppressWarnings("unused")
public interface SystemNotificationRepository extends JpaRepository<SystemNotification,Long> {

}
