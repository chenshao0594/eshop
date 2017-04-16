package com.smartshop.eshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartshop.eshop.domain.Category;
import com.smartshop.eshop.domain.MerchantStore;


public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
	

	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cd.seUrl like ?2 and cm.id = ?1 order by c.sortOrder asc")
	List<Category> listByFriendlyUrl(Long storeId, String friendlyUrl);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cd.seUrl=?2 and cm.id = ?1")
	Category findByFriendlyUrl(Long storeId, String friendlyUrl);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cd.name like %?2% and cdl.id=?3 and cm.id = ?1 order by c.sortOrder asc")
	List<Category> findByName(Long storeId, String name, Long languageId);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where c.code=?2 and cm.id = ?1")
	Category findByCode(Long storeId, String code);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where c.code in (?2) and cdl.id=?3 and cm.id = ?1 order by c.sortOrder asc")
	List<Category> findByCodes(Long storeId, List<String> codes, Long languageId);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where c.id in (?2) and cdl.id=?3 and cm.id = ?1 order by c.sortOrder asc")
	List<Category> findByIds(Long storeId, List<Long> ids, Long languageId);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cdl.id=?2 and c.id = ?1")
	Category findById(Long categoryId, Long languageId);
	
	//@Query("select c from Category c left join fetch c.descriptions cd join fetch c.merchantStore cm where cd.language.id=?2 and c.id = ?1")
	//List<Category> findById(Long categoryId, Long languageId);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.code=?1 and c.code=?2")
	public Category findByCode(String merchantStoreCode, String code);
	
	@Query("select c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where c.id=?1")
	public Category findOne(Long categoryId);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.id=?1 and c.lineage like %?2% order by c.lineage, c.sortOrder asc")
	public List<Category> findByLineage(Long merchantId, String linenage);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.code= ?1 and c.lineage like %?2% order by c.lineage, c.sortOrder asc")
	public List<Category> findByLineage(String storeCode, String linenage);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.id=?1 and c.depth >= ?2 order by c.lineage, c.sortOrder asc")
	public List<Category> findByDepth(Long merchantId, int depth);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.id=?1 and cdl.id=?3 and c.depth >= ?2 order by c.lineage, c.sortOrder asc")
	public List<Category> findByDepth(Long merchantId, int depth, Long languageId);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch c.merchantStore cm left join fetch c.parent cp where cm.id=?1 and cp.id=?2 and cd.language.id=?3 order by c.lineage, c.sortOrder asc")
	public List<Category> findByParent(Long merchantId, Long parentId, Long languageId);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm left join fetch c.parent cp where cp.id=?1 and cdl.id=?2 order by c.lineage, c.sortOrder asc")
	public List<Category> findByParent(Long parentId, Long languageId);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.id=?1 and cdl.id=?2 order by c.lineage, c.sortOrder asc")
	public List<Category> findByStore(Long merchantId, Long languageId);
	
	@Query("select distinct c from Category c left join fetch c.descriptions cd join fetch cd.language cdl join fetch c.merchantStore cm where cm.id=?1 order by c.lineage, c.sortOrder asc")
	public List<Category> findByStore(Long merchantId);

	List<Object[]> countProductsByCategories(MerchantStore store,
			List<Long> categoryIds);

	List<Category> listByStoreAndParent(MerchantStore store, Category category);
	
}
