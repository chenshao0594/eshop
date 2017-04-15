package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOptionValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionValue entity.
 */
public interface CustomerOptionValueSearchRepository extends ElasticsearchRepository<CustomerOptionValue, Long> {
}
