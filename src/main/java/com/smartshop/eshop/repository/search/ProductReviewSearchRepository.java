package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductReview;

/**
 * Spring Data Elasticsearch repository for the ProductReview entity.
 */
public interface ProductReviewSearchRepository extends ElasticsearchRepository<ProductReview, Long> {
}
