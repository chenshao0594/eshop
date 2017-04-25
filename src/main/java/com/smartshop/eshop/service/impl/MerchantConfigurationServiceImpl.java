package com.smartshop.eshop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.enumeration.MerchantConfigurationEnum;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.MerchantConfigurationRepository;
import com.smartshop.eshop.repository.search.MerchantConfigurationSearchRepository;
import com.smartshop.eshop.service.MerchantConfigurationService;
import com.smartshop.payment.dto.MerchantConfigDTO;

/**
 * Service Implementation for managing MerchantConfiguration.
 */
@Service
@Transactional
public class MerchantConfigurationServiceImpl extends AbstractDomainServiceImpl<MerchantConfiguration, Long>
		implements MerchantConfigurationService {

	private final Logger LOGGER = LoggerFactory.getLogger(MerchantConfigurationServiceImpl.class);
	private final MerchantConfigurationRepository merchantConfigurationRepository;
	private final MerchantConfigurationSearchRepository merchantConfigurationSearchRepository;

	public MerchantConfigurationServiceImpl(MerchantConfigurationRepository merchantConfigurationRepository,
			MerchantConfigurationSearchRepository merchantConfigurationSearchRepository) {
		super(merchantConfigurationRepository, merchantConfigurationSearchRepository);
		this.merchantConfigurationRepository = merchantConfigurationRepository;
		this.merchantConfigurationSearchRepository = merchantConfigurationSearchRepository;
	}

	@Override
	public MerchantConfiguration getMerchantConfiguration(String key, MerchantStore store) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(MerchantConfiguration entity) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MerchantConfiguration> listByStore(MerchantStore store) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MerchantConfiguration> listByType(MerchantConfigurationEnum type, MerchantStore store)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MerchantConfigDTO getMerchantConfig(MerchantStore store) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveMerchantConfig(MerchantConfigDTO config, MerchantStore store) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
