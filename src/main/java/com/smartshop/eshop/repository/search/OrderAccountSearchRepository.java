package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderAccount entity.
 */
public interface OrderAccountSearchRepository extends ElasticsearchRepository<OrderAccount, Long> {
}
