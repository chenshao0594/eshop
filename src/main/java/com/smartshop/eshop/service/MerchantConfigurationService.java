package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.enumeration.MerchantConfigurationEnum;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.payment.dto.MerchantConfigDTO;

/**
 * Service Interface for managing MerchantConfiguration.
 */
public interface MerchantConfigurationService extends AbstractDomainService<MerchantConfiguration, Long> {
MerchantConfiguration getMerchantConfiguration(String key, MerchantStore store) throws BusinessException;
	
	public void saveOrUpdate(MerchantConfiguration entity) throws BusinessException;

	List<MerchantConfiguration> listByStore(MerchantStore store)
			throws BusinessException;

	List<MerchantConfiguration> listByType(MerchantConfigurationEnum type,
			MerchantStore store) throws BusinessException;

	MerchantConfigDTO getMerchantConfig(MerchantStore store)
			throws BusinessException;

	void saveMerchantConfig(MerchantConfigDTO config, MerchantStore store)
			throws BusinessException;


}