package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.DigitalProduct;

/**
 * Spring Data Elasticsearch repository for the DigitalProduct entity.
 */
public interface DigitalProductSearchRepository extends ElasticsearchRepository<DigitalProduct, Long> {
}
