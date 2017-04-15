package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOptionValueDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOptionValueDescription entity.
 */
public interface CustomerOptionValueDescriptionSearchRepository extends ElasticsearchRepository<CustomerOptionValueDescription, Long> {
}
