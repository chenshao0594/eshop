package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductOptionValue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductOptionValue entity.
 */
public interface ProductOptionValueSearchRepository extends ElasticsearchRepository<ProductOptionValue, Long> {
}
