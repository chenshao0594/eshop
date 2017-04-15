package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOptin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOptin entity.
 */
public interface CustomerOptinSearchRepository extends ElasticsearchRepository<CustomerOptin, Long> {
}
