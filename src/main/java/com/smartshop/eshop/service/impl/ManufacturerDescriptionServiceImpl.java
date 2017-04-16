package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.ManufacturerDescription;
import com.smartshop.eshop.repository.ManufacturerDescriptionRepository;
import com.smartshop.eshop.repository.search.ManufacturerDescriptionSearchRepository;
import com.smartshop.eshop.service.ManufacturerDescriptionService;

/**
 * Service Implementation for managing ManufacturerDescription.
 */
@Service
@Transactional
public class ManufacturerDescriptionServiceImpl extends AbstractDomainServiceImpl<ManufacturerDescription, Long>
		implements ManufacturerDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(ManufacturerDescriptionServiceImpl.class);
	private final ManufacturerDescriptionRepository manufacturerDescriptionRepository;
	private final ManufacturerDescriptionSearchRepository manufacturerDescriptionSearchRepository;

	public ManufacturerDescriptionServiceImpl(ManufacturerDescriptionRepository manufacturerDescriptionRepository,
			ManufacturerDescriptionSearchRepository manufacturerDescriptionSearchRepository) {
		super(manufacturerDescriptionRepository, manufacturerDescriptionSearchRepository);
		this.manufacturerDescriptionRepository = manufacturerDescriptionRepository;
		this.manufacturerDescriptionSearchRepository = manufacturerDescriptionSearchRepository;
	}

}
