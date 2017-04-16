package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.MerchantStore;

/**
 * Spring Data Elasticsearch repository for the MerchantStore entity.
 */
public interface MerchantStoreSearchRepository extends ElasticsearchRepository<MerchantStore, Long> {
}
