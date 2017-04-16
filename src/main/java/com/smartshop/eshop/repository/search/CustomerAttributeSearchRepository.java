package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CustomerAttribute;

/**
 * Spring Data Elasticsearch repository for the CustomerAttribute entity.
 */
public interface CustomerAttributeSearchRepository extends ElasticsearchRepository<CustomerAttribute, Long> {
}
