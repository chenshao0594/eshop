package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderProduct entity.
 */
public interface OrderProductSearchRepository extends ElasticsearchRepository<OrderProduct, Long> {
}
