package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductDescription entity.
 */
public interface ProductDescriptionSearchRepository extends ElasticsearchRepository<ProductDescription, Long> {
}
