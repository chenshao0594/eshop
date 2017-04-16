package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.SalesOrder;

/**
 * Spring Data Elasticsearch repository for the SalesOrder entity.
 */
public interface SalesOrderSearchRepository extends ElasticsearchRepository<SalesOrder, Long> {
}
