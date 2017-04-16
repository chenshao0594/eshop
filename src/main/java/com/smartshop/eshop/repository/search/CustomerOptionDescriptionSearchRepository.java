package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOptionDescription;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionDescription entity.
 */
public interface CustomerOptionDescriptionSearchRepository extends ElasticsearchRepository<CustomerOptionDescription, Long> {
}
