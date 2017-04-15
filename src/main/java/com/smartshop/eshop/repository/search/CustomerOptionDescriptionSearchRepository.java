package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOptionDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionDescription entity.
 */
public interface CustomerOptionDescriptionSearchRepository extends ElasticsearchRepository<CustomerOptionDescription, Long> {
}
