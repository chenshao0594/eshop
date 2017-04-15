package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.MerchantConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MerchantConfiguration entity.
 */
public interface MerchantConfigurationSearchRepository extends ElasticsearchRepository<MerchantConfiguration, Long> {
}
