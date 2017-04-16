package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Currency;

/**
 * Spring Data Elasticsearch repository for the Currency entity.
 */
public interface CurrencySearchRepository extends ElasticsearchRepository<Currency, Long> {
}
