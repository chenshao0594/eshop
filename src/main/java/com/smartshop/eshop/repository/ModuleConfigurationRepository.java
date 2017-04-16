package com.smartshop.eshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartshop.eshop.domain.IntegrationModule;

public interface ModuleConfigurationRepository extends JpaRepository<IntegrationModule, Long> {

	List<IntegrationModule> findByModule(String moduleName);

	IntegrationModule findByCode(String code);

}
