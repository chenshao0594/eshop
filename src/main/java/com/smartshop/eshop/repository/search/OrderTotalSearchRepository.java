package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderTotal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderTotal entity.
 */
public interface OrderTotalSearchRepository extends ElasticsearchRepository<OrderTotal, Long> {
}
