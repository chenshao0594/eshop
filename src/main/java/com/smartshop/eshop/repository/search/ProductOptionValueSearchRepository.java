package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductOptionValue;

/**
 * Spring Data Elasticsearch repository for the ProductOptionValue entity.
 */
public interface ProductOptionValueSearchRepository extends ElasticsearchRepository<ProductOptionValue, Long> {
}
