package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.TaxRateDescription;

/**
 * Spring Data Elasticsearch repository for the TaxRateDescription entity.
 */
public interface TaxRateDescriptionSearchRepository extends ElasticsearchRepository<TaxRateDescription, Long> {
}
