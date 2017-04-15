package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.TaxRateDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaxRateDescription entity.
 */
public interface TaxRateDescriptionSearchRepository extends ElasticsearchRepository<TaxRateDescription, Long> {
}
