package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.TaxClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TaxClass entity.
 */
public interface TaxClassSearchRepository extends ElasticsearchRepository<TaxClass, Long> {
}
