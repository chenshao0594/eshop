package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderStatusHistory;

/**
 * Spring Data Elasticsearch repository for the OrderStatusHistory entity.
 */
public interface OrderStatusHistorySearchRepository extends ElasticsearchRepository<OrderStatusHistory, Long> {
}
