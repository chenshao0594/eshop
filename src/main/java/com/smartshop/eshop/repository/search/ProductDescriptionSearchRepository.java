package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductDescription;

/**
 * Spring Data Elasticsearch repository for the ProductDescription entity.
 */
public interface ProductDescriptionSearchRepository extends ElasticsearchRepository<ProductDescription, Long> {
}
