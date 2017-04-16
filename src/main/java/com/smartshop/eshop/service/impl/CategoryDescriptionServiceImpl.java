package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.CategoryDescription;
import com.smartshop.eshop.repository.CategoryDescriptionRepository;
import com.smartshop.eshop.repository.search.CategoryDescriptionSearchRepository;
import com.smartshop.eshop.service.CategoryDescriptionService;

/**
 * Service Implementation for managing CategoryDescription.
 */
@Service
@Transactional
public class CategoryDescriptionServiceImpl extends AbstractDomainServiceImpl<CategoryDescription, Long>
		implements CategoryDescriptionService {

	private final Logger LOGGER = LoggerFactory.getLogger(CategoryDescriptionServiceImpl.class);
	private final CategoryDescriptionRepository categoryDescriptionRepository;
	private final CategoryDescriptionSearchRepository categoryDescriptionSearchRepository;

	public CategoryDescriptionServiceImpl(CategoryDescriptionRepository categoryDescriptionRepository,
			CategoryDescriptionSearchRepository categoryDescriptionSearchRepository) {
		super(categoryDescriptionRepository, categoryDescriptionSearchRepository);
		this.categoryDescriptionRepository = categoryDescriptionRepository;
		this.categoryDescriptionSearchRepository = categoryDescriptionSearchRepository;
	}

}
