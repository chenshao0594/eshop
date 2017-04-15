package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.OrderProductDownload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OrderProductDownload entity.
 */
public interface OrderProductDownloadSearchRepository extends ElasticsearchRepository<OrderProductDownload, Long> {
}
