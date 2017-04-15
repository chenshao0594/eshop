package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.IntegrationModule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IntegrationModule entity.
 */
public interface IntegrationModuleSearchRepository extends ElasticsearchRepository<IntegrationModule, Long> {
}
