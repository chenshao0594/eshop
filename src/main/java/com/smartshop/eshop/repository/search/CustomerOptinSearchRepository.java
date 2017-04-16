package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOptin;

/**
 * Spring Data Elasticsearch repository for the CustomerOptin entity.
 */
public interface CustomerOptinSearchRepository extends ElasticsearchRepository<CustomerOptin, Long> {
}
