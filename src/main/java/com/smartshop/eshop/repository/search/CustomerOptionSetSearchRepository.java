package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOptionSet;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionSet entity.
 */
public interface CustomerOptionSetSearchRepository extends ElasticsearchRepository<CustomerOptionSet, Long> {
}
