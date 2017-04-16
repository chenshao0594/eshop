package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductOptionDescription;

/**
 * Spring Data Elasticsearch repository for the ProductOptionDescription entity.
 */
public interface ProductOptionDescriptionSearchRepository
		extends ElasticsearchRepository<ProductOptionDescription, Long> {
}
