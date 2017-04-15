package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductReview;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductReview entity.
 */
public interface ProductReviewSearchRepository extends ElasticsearchRepository<ProductReview, Long> {
}
