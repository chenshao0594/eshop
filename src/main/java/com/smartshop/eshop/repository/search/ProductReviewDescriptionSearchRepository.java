package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductReviewDescription;

/**
 * Spring Data Elasticsearch repository for the ProductReviewDescription entity.
 */
public interface ProductReviewDescriptionSearchRepository
		extends ElasticsearchRepository<ProductReviewDescription, Long> {
}
