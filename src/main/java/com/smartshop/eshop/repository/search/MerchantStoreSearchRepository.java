package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.MerchantStore;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MerchantStore entity.
 */
public interface MerchantStoreSearchRepository extends ElasticsearchRepository<MerchantStore, Long> {
}
