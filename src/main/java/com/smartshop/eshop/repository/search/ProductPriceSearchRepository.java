package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductPrice;

/**
 * Spring Data Elasticsearch repository for the ProductPrice entity.
 */
public interface ProductPriceSearchRepository extends ElasticsearchRepository<ProductPrice, Long> {
}
