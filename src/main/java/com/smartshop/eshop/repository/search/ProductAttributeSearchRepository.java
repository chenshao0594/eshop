package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductAttribute entity.
 */
public interface ProductAttributeSearchRepository extends ElasticsearchRepository<ProductAttribute, Long> {
}
