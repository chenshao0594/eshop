package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductAttribute;

/**
 * Spring Data Elasticsearch repository for the ProductAttribute entity.
 */
public interface ProductAttributeSearchRepository extends ElasticsearchRepository<ProductAttribute, Long> {
}
