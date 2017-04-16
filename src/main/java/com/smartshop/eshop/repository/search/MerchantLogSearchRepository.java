package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.MerchantLog;

/**
 * Spring Data Elasticsearch repository for the MerchantLog entity.
 */
public interface MerchantLogSearchRepository extends ElasticsearchRepository<MerchantLog, Long> {
}
