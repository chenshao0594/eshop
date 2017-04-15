package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.CategoryService;
import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.repository.CategoryRepository;
import com.smartshop.eshop.repository.search.CategorySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractDomainServiceImpl< Category, Long> implements CategoryService{

    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategorySearchRepository categorySearchRepository;
    
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategorySearchRepository categorySearchRepository) {
        super(categoryRepository,categorySearchRepository);
        this.categoryRepository = categoryRepository;
        this.categorySearchRepository = categorySearchRepository;
    }
    
}
