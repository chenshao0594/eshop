package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Optin;

/**
 * Spring Data Elasticsearch repository for the Optin entity.
 */
public interface OptinSearchRepository extends ElasticsearchRepository<Optin, Long> {
}
