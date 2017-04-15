package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductImage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductImage entity.
 */
public interface ProductImageSearchRepository extends ElasticsearchRepository<ProductImage, Long> {
}
