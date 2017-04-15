package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductReviewDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductReviewDescription entity.
 */
public interface ProductReviewDescriptionSearchRepository extends ElasticsearchRepository<ProductReviewDescription, Long> {
}
