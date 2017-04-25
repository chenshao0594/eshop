package com.smartshop.eshop.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.TaxClass;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.TaxClassRepository;
import com.smartshop.eshop.repository.search.TaxClassSearchRepository;
import com.smartshop.eshop.service.TaxClassService;

/**
 * Service Implementation for managing TaxClass.
 */
@Service
@Transactional
public class TaxClassServiceImpl extends AbstractDomainServiceImpl<TaxClass, Long> implements TaxClassService {

	private final Logger LOGGER = LoggerFactory.getLogger(TaxClassServiceImpl.class);
	private final TaxClassRepository taxClassRepository;
	private final TaxClassSearchRepository taxClassSearchRepository;

	public TaxClassServiceImpl(TaxClassRepository taxClassRepository,
			TaxClassSearchRepository taxClassSearchRepository) {
		super(taxClassRepository, taxClassSearchRepository);
		this.taxClassRepository = taxClassRepository;
		this.taxClassSearchRepository = taxClassSearchRepository;
	}

	@Override
	public List<TaxClass> listByStore(MerchantStore store) throws BusinessException {
		return taxClassRepository.findByStore(store.getId());
	}

	@Override
	public TaxClass getByCode(String code) throws BusinessException {
		return taxClassRepository.findByCode(code);
	}

	@Override
	public TaxClass getByCode(String code, MerchantStore store) throws BusinessException {
		return taxClassRepository.findByStoreAndCode(store.getId(), code);
	}

	@Override
	public TaxClass getById(Long id) {
		return taxClassRepository.findOne(id);
	}
}
