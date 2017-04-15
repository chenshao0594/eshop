package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductImageDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductImageDescription entity.
 */
public interface ProductImageDescriptionSearchRepository extends ElasticsearchRepository<ProductImageDescription, Long> {
}
