package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderProductAttribute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderProductAttribute entity.
 */
public interface OrderProductAttributeSearchRepository extends ElasticsearchRepository<OrderProductAttribute, Long> {
}
