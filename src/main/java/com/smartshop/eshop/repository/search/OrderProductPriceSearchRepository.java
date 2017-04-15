package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderProductPrice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderProductPrice entity.
 */
public interface OrderProductPriceSearchRepository extends ElasticsearchRepository<OrderProductPrice, Long> {
}
