package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.service.CategoryService;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/api/" + CategoryController.SECTION_KEY)
public class CategoryController extends AbstractDomainController<Category, Long> {

	private final Logger log = LoggerFactory.getLogger(CategoryController.class);
	public static final String SECTION_KEY = "categories";
	private static final String ENTITY_NAME = "category";

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		super(categoryService);
		this.categoryService = categoryService;
	}
	@Override
	protected Category postCreate(Category category){
		if(category.getParent()==null){
			return category;
		}
		if (category.getParent().getId() == -1) {// this is a root category
			category.setParent(null);
			category.setLineage("/");
			category.setDepth(0);
		}else{
			Category parent = new Category();
			parent.setId(category.getParent().getId());
			parent.setMerchantStore(category.getMerchantStore());
			try {
				categoryService.addChild(parent, category);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		categoryService.saveOrUpdate(category);
		return category;

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