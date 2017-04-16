package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.TaxRate;

/**
 * Spring Data Elasticsearch repository for the TaxRate entity.
 */
public interface TaxRateSearchRepository extends ElasticsearchRepository<TaxRate, Long> {
}
