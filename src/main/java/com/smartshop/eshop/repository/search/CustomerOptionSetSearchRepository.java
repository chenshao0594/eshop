package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOptionSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionSet entity.
 */
public interface CustomerOptionSetSearchRepository extends ElasticsearchRepository<CustomerOptionSet, Long> {
}
