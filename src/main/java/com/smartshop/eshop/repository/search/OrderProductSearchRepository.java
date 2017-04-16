package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderProduct;

/**
 * Spring Data Elasticsearch repository for the OrderProduct entity.
 */
public interface OrderProductSearchRepository extends ElasticsearchRepository<OrderProduct, Long> {
}
