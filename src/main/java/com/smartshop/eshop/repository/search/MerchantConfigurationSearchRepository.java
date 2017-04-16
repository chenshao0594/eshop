package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.MerchantConfiguration;

/**
 * Spring Data Elasticsearch repository for the MerchantConfiguration entity.
 */
public interface MerchantConfigurationSearchRepository extends ElasticsearchRepository<MerchantConfiguration, Long> {
}
