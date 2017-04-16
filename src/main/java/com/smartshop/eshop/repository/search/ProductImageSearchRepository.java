package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductImage;

/**
 * Spring Data Elasticsearch repository for the ProductImage entity.
 */
public interface ProductImageSearchRepository extends ElasticsearchRepository<ProductImage, Long> {
}
