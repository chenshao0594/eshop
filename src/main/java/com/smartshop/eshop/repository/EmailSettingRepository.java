package com.smartshop.eshop.repository;

import com.smartshop.eshop.domain.EmailSetting;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmailSetting entity.
 */
@SuppressWarnings("unused")
public interface EmailSettingRepository extends JpaRepository<EmailSetting,Long> {

}
