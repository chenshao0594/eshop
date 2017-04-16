package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.SystemConfiguration;

/**
 * Spring Data Elasticsearch repository for the SystemConfiguration entity.
 */
public interface SystemConfigurationSearchRepository extends ElasticsearchRepository<SystemConfiguration, Long> {
}
