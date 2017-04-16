package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.OrderProductDownload;

/**
 * Spring Data Elasticsearch repository for the OrderProductDownload entity.
 */
public interface OrderProductDownloadSearchRepository extends ElasticsearchRepository<OrderProductDownload, Long> {
}
