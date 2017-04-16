package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductRelationship;

/**
 * Spring Data Elasticsearch repository for the ProductRelationship entity.
 */
public interface ProductRelationshipSearchRepository extends ElasticsearchRepository<ProductRelationship, Long> {
}
