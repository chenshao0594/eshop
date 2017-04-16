package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderProductAttribute;

/**
 * Spring Data Elasticsearch repository for the OrderProductAttribute entity.
 */
public interface OrderProductAttributeSearchRepository extends ElasticsearchRepository<OrderProductAttribute, Long> {
}
