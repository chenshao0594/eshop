package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductOption;

/**
 * Spring Data Elasticsearch repository for the ProductOption entity.
 */
public interface ProductOptionSearchRepository extends ElasticsearchRepository<ProductOption, Long> {
}
