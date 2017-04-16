package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOptionValue;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionValue entity.
 */
public interface CustomerOptionValueSearchRepository extends ElasticsearchRepository<CustomerOptionValue, Long> {
}
