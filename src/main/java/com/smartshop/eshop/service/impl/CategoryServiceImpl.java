package com.smartshop.eshop.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.smartshop.eshop.config.Constants;
import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.domain.CategoryDescription;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.repository.CategoryRepository;
import com.smartshop.eshop.repository.search.CategorySearchRepository;
import com.smartshop.eshop.service.CategoryService;
import com.smartshop.eshop.service.ProductService;

/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractDomainServiceImpl<Category, Long> implements CategoryService {

	private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	private final CategoryRepository categoryRepository;
	private final CategorySearchRepository categorySearchRepository;
	@PersistenceContext
	private EntityManager em;

	@Inject
	private ProductService productService;

	public CategoryServiceImpl(CategoryRepository categoryRepository,
			CategorySearchRepository categorySearchRepository) {
		super(categoryRepository, categorySearchRepository);
		this.categoryRepository = categoryRepository;
		this.categorySearchRepository = categorySearchRepository;
	}

	public void create(Category category) {
		super.save(category);
		StringBuilder lineage = new StringBuilder();
		Category parent = category.getParent();
		if (parent != null && parent.getId() != null && parent.getId() != 0) {
			lineage.append(parent.getLineage()).append("/").append(parent.getId());
			category.setDepth(parent.getDepth() + 1);
		} else {
			lineage.append("/");
			category.setDepth(0);
		}
		category.setLineage(lineage.toString());
		super.update(category);

	}

	@Override
	public List<Object[]> countProductsByCategories(MerchantStore store, List<Long> categoryIds) {

		StringBuilder qs = new StringBuilder();
		qs.append("select categories, count(product.id) from Product product ");
		qs.append("inner join product.categories categories ");
		qs.append("where categories.id in (:cid) ");
		qs.append("and product.available=true and product.dateAvailable<=:dt ");
		qs.append("group by categories.id");

		String hql = qs.toString();
		Query q = this.em.createQuery(hql);
		q.setParameter("cid", categoryIds);
		q.setParameter("dt", new Date());

		@SuppressWarnings("unchecked")
		List<Object[]> counts = q.getResultList();

		return counts;
	}

	@Override
	public List<Category> listByCodes(MerchantStore store, List<String> codes, Language language) {
		return categoryRepository.findByCodes(store.getId(), codes, language.getId());
	}

	@Override
	public List<Category> listByIds(MerchantStore store, List<Long> ids, Language language) {
		return categoryRepository.findByIds(store.getId(), ids, language.getId());
	}

	@Override
	public Category getByLanguage(long categoryId, Language language) {
		return categoryRepository.findById(categoryId, language.getId());
	}

	@Override
	public void saveOrUpdate(Category category) {

		// save or update (persist and attach entities
		if (category.getId() != null && category.getId() > 0) {
			super.update(category);
		} else {
			super.save(category);
		}
	}
	@Override
	public List<Category> listByLineage(MerchantStore store, String lineage) {
		return categoryRepository.findByLineage(store.getId(), lineage);
	}
	@Override
	public List<Category> listByLineage(String storeCode, String lineage) {
		return categoryRepository.findByLineage(storeCode, lineage);
	}

	@Override
	public List<Category> listBySeUrl(MerchantStore store, String seUrl) {
		return categoryRepository.listByFriendlyUrl(store.getId(), seUrl);
	}

	@Override
	public Category getBySeUrl(MerchantStore store, String seUrl) {
		return categoryRepository.findByFriendlyUrl(store.getId(), seUrl);
	}

	@Override
	public Category getByCode(MerchantStore store, String code) {
		return categoryRepository.findByCode(store.getId(), code);
	}

	@Override
	public Category getByCode(String storeCode, String code) {
		return categoryRepository.findByCode(storeCode, code);
	}

	@Override
	public Category getById(Long id) {

		return categoryRepository.findOne(id);
	}

	@Override
	public List<Category> listByParent(Category category) {
		return this.listByStoreAndParentImpl(null, category);
	}

	@Override
	public List<Category> listByStoreAndParent(MerchantStore store, Category category) {
		return this.listByStoreAndParentImpl(store, category);
	}

	@Override
	public List<Category> listByParent(Category category, Language language) {
		Assert.notNull(category, "Category cannot be null");
		Assert.notNull(language, "Language cannot be null");
		Assert.notNull(category.getMerchantStore(), "category.merchantStore cannot be null");
		return categoryRepository.findByParent(category.getId(), language.getId());
	}

	@Override
	public void addCategoryDescription(Category category, CategoryDescription description) {
		category.getDescriptions().add(description);
		description.setCategory(category);
		update(category);
	}

	// @Override
	public void delete(Category category) {
		// get category with lineage (subcategories)
		StringBuilder lineage = new StringBuilder();
		lineage.append(category.getLineage()).append(category.getId()).append(Constants.SLASH);
		List<Category> categories = this.listByLineage(category.getMerchantStore(), lineage.toString());

		Category dbCategory = this.getById(category.getId());

		if (dbCategory != null && dbCategory.getId().longValue() == category.getId().longValue()) {

			categories.add(dbCategory);

			Collections.reverse(categories);

			List<Long> categoryIds = new ArrayList<Long>();

			for (Category c : categories) {
				categoryIds.add(c.getId());
			}

			List<Product> products = productService.getProducts(categoryIds);
			// org.hibernate.Session session =
			// em.unwrap(org.hibernate.Session.class);// need
			// // to
			// // refresh
			// // the
			// // session
			// // to
			// // update
			// // all
			// // product
			// // categories

			for (Product product : products) {
				// session.evict(product);// refresh product so we get all
				// product
				// categories
				Product dbProduct = productService.getById(product.getId());
				Set<Category> productCategories = dbProduct.getCategories();
				if (productCategories.size() > 1) {
					for (Category c : categories) {
						productCategories.remove(c);
						productService.update(dbProduct);
					}

					if (product.getCategories() == null || product.getCategories().size() == 0) {
						productService.delete(dbProduct.getId());
					}
				} else {
					productService.delete(dbProduct.getId());
				}
			}
			Category categ = this.getById(category.getId());
			categoryRepository.delete(categ);

		}

	}

	@Override
	public CategoryDescription getDescription(Category category, Language language) {
		for (CategoryDescription description : category.getDescriptions()) {
			if (description.getLanguage().equals(language)) {
				return description;
			}
		}
		return null;
	}

	@Override
	public void addChild(Category parent, Category child) throws BusinessException {

		if (child == null || child.getMerchantStore() == null) {
			throw new BusinessException("Child category and merchant store should not be null");
		}

		try {
			if (parent == null) {
				// assign to root
				child.setParent(null);
				child.setDepth(0);
				// child.setLineage(new
				// StringBuilder().append("/").append(child.getId()).append("/").toString());
				child.setLineage("/");
			} else {
				Category p = this.getById(parent.getId());// parent
				String lineage = p.getLineage();
				int depth = p.getDepth();// TODO sometimes null
				child.setParent(p);
				child.setDepth(depth + 1);
				child.setLineage(new StringBuilder().append(lineage).append(p.getId()).append("/").toString());
			}
			update(child);
			StringBuilder childLineage = new StringBuilder();
			childLineage.append(child.getLineage()).append(child.getId()).append("/");
			List<Category> subCategories = listByLineage(child.getMerchantStore(), childLineage.toString());
			// ajust all sub categories lineages
			if (subCategories != null && subCategories.size() > 0) {
				for (Category subCategory : subCategories) {
					if (child.getId() != subCategory.getId()) {
						addChild(child, subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Category> listByDepth(MerchantStore store, int depth) {
		return categoryRepository.findByDepth(store.getId(), depth);
	}

	@Override
	public List<Category> listByDepth(MerchantStore store, int depth, Language language) {
		return categoryRepository.findByDepth(store.getId(), depth, language.getId());
	}

	@Override
	public List<Category> getByName(MerchantStore store, String name, Language language) {
		return categoryRepository.findByName(store.getId(), name, language.getId());
	}

	@Override
	public List<Category> listByStore(MerchantStore store) {
		return categoryRepository.findByStore(store.getId());
	}

	@Override
	public List<Category> listByStore(MerchantStore store, Language language) {
		return categoryRepository.findByStore(store.getId(), language.getId());
	}

	private List<Category> listByStoreAndParentImpl(MerchantStore store, Category category) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select c from Category c join fetch c.merchantStore cm ");

		if (store == null) {
			if (category == null) {
				// query.from(qCategory)
				queryBuilder.append(" where c.parent IsNull ");
				// .where(qCategory.parent.isNull())
				// .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			} else {
				// query.from(qCategory)
				queryBuilder.append(" join fetch c.parent cp where cp.id =:cid ");
				// .where(qCategory.parent.eq(category))
				// .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			}
		} else {
			if (category == null) {
				// query.from(qCategory)
				queryBuilder.append(" where c.parent IsNull and cm.id=:mid ");
				// .where(qCategory.parent.isNull()
				// .and(qCategory.merchantStore.eq(store)))
				// .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			} else {
				// query.from(qCategory)
				queryBuilder.append(" join fetch c.parent cp where cp.id =:cId and cm.id=:mid ");
				// .where(qCategory.parent.eq(category)
				// .and(qCategory.merchantStore.eq(store)))
				// .orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			}
		}

		queryBuilder.append(" order by c.sortOrder asc");

		String hql = queryBuilder.toString();
		Query q = this.em.createQuery(hql);

		q.setParameter("cid", category.getId());
		if (store != null) {
			q.setParameter("mid", store.getId());
		}

		return q.getResultList();
	}

}
