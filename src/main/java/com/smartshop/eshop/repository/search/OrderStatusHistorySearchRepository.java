package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderStatusHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderStatusHistory entity.
 */
public interface OrderStatusHistorySearchRepository extends ElasticsearchRepository<OrderStatusHistory, Long> {
}
