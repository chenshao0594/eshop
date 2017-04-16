package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductAvailability;

/**
 * Spring Data Elasticsearch repository for the ProductAvailability entity.
 */
public interface ProductAvailabilitySearchRepository extends ElasticsearchRepository<ProductAvailability, Long> {
}
