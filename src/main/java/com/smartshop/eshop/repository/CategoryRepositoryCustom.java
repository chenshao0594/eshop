package com.smartshop.eshop.repository;

import java.util.List;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.domain.MerchantStore;

public interface CategoryRepositoryCustom {

	List<Object[]> countProductsByCategories(MerchantStore store, List<Long> categoryIds);

	List<Category> listByStoreAndParent(MerchantStore store, Category category);

}
