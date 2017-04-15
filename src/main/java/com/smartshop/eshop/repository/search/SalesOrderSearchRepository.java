package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.SalesOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SalesOrder entity.
 */
public interface SalesOrderSearchRepository extends ElasticsearchRepository<SalesOrder, Long> {
}
