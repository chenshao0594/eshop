package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderProductPrice;

/**
 * Spring Data Elasticsearch repository for the OrderProductPrice entity.
 */
public interface OrderProductPriceSearchRepository extends ElasticsearchRepository<OrderProductPrice, Long> {
}
