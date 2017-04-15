package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductAvailability;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductAvailability entity.
 */
public interface ProductAvailabilitySearchRepository extends ElasticsearchRepository<ProductAvailability, Long> {
}
