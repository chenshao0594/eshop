package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing IntegrationModule.
 */
public interface IntegrationModuleService extends AbstractDomainService<IntegrationModule, Long> {
	List<IntegrationModule> getIntegrationModules(String module);

	IntegrationModule getByCode(String moduleCode);
	
	void createOrUpdateModule(String json) throws BusinessException;
	

}