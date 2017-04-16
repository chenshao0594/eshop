package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerOption;

/**
 * Spring Data Elasticsearch repository for the CustomerOption entity.
 */
public interface CustomerOptionSearchRepository extends ElasticsearchRepository<CustomerOption, Long> {
}
