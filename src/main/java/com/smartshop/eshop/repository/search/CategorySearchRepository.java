package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Category;

/**
 * Spring Data Elasticsearch repository for the Category entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, Long> {
}
