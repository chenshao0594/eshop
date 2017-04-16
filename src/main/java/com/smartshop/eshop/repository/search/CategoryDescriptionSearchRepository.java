package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CategoryDescription;

/**
 * Spring Data Elasticsearch repository for the CategoryDescription entity.
 */
public interface CategoryDescriptionSearchRepository extends ElasticsearchRepository<CategoryDescription, Long> {
}
