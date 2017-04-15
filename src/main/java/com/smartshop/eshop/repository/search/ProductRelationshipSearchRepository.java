package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductRelationship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductRelationship entity.
 */
public interface ProductRelationshipSearchRepository extends ElasticsearchRepository<ProductRelationship, Long> {
}
