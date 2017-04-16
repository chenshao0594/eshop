package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductImageDescription;

/**
 * Spring Data Elasticsearch repository for the ProductImageDescription entity.
 */
public interface ProductImageDescriptionSearchRepository extends ElasticsearchRepository<ProductImageDescription, Long> {
}
