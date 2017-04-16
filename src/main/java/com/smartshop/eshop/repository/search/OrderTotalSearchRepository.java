package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderTotal;

/**
 * Spring Data Elasticsearch repository for the OrderTotal entity.
 */
public interface OrderTotalSearchRepository extends ElasticsearchRepository<OrderTotal, Long> {
}
