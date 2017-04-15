package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderAccountProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderAccountProduct entity.
 */
public interface OrderAccountProductSearchRepository extends ElasticsearchRepository<OrderAccountProduct, Long> {
}
