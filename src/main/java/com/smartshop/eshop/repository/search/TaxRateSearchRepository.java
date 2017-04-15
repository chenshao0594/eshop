package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.TaxRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaxRate entity.
 */
public interface TaxRateSearchRepository extends ElasticsearchRepository<TaxRate, Long> {
}
