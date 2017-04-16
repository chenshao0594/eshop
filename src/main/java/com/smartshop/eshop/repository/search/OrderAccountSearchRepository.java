package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderAccount;

/**
 * Spring Data Elasticsearch repository for the OrderAccount entity.
 */
public interface OrderAccountSearchRepository extends ElasticsearchRepository<OrderAccount, Long> {
}
