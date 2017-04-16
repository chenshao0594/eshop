package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductOptionValueDescription;

/**
 * Spring Data Elasticsearch repository for the ProductOptionValueDescription entity.
 */
public interface ProductOptionValueDescriptionSearchRepository extends ElasticsearchRepository<ProductOptionValueDescription, Long> {
}
