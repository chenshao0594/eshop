package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.MerchantLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MerchantLog entity.
 */
public interface MerchantLogSearchRepository extends ElasticsearchRepository<MerchantLog, Long> {
}
