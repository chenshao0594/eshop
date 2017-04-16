package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductType;

/**
 * Spring Data Elasticsearch repository for the ProductType entity.
 */
public interface ProductTypeSearchRepository extends ElasticsearchRepository<ProductType, Long> {
}
