package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderAccountProduct;

/**
 * Spring Data Elasticsearch repository for the OrderAccountProduct entity.
 */
public interface OrderAccountProductSearchRepository extends ElasticsearchRepository<OrderAccountProduct, Long> {
}
