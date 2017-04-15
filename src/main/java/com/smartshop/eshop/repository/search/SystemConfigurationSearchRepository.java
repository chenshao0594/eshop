package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.SystemConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SystemConfiguration entity.
 */
public interface SystemConfigurationSearchRepository extends ElasticsearchRepository<SystemConfiguration, Long> {
}
