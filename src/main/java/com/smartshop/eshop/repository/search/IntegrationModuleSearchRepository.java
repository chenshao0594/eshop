package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.IntegrationModule;

/**
 * Spring Data Elasticsearch repository for the IntegrationModule entity.
 */
public interface IntegrationModuleSearchRepository extends ElasticsearchRepository<IntegrationModule, Long> {
}
