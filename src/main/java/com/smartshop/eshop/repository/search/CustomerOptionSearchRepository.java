package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CustomerOption;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CustomerOption entity.
 */
public interface CustomerOptionSearchRepository extends ElasticsearchRepository<CustomerOption, Long> {
}
