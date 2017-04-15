package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.Optin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Optin entity.
 */
public interface OptinSearchRepository extends ElasticsearchRepository<Optin, Long> {
}
