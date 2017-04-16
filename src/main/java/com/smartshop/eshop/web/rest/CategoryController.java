package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.service.CategoryService;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api/"+ CategoryController.SECTION_KEY)
public class CategoryController extends AbstractDomainController< Category, Long>{

    private final Logger log = LoggerFactory.getLogger(CategoryController.class);
    public static final String SECTION_KEY = "categories";
    private static final String ENTITY_NAME = "category";
        
     private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
        this.categoryService = categoryService;
    }    
    @Override
    protected String getSectionKey() {
        return SECTION_KEY;
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

}