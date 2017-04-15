package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CategoryDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CategoryDescription entity.
 */
public interface CategoryDescriptionSearchRepository extends ElasticsearchRepository<CategoryDescription, Long> {
}
