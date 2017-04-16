package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.TaxClass;

/**
 * Spring Data Elasticsearch repository for the TaxClass entity.
 */
public interface TaxClassSearchRepository extends ElasticsearchRepository<TaxClass, Long> {
}
