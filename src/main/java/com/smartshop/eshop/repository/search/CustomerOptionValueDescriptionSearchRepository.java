package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionValueDescription
 * entity.
 */
public interface CustomerOptionValueDescriptionSearchRepository
		extends ElasticsearchRepository<CustomerOptionValueDescription, Long> {
}
