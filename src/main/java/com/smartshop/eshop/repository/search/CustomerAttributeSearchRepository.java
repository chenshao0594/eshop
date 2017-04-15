package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerAttribute entity.
 */
public interface CustomerAttributeSearchRepository extends ElasticsearchRepository<CustomerAttribute, Long> {
}
