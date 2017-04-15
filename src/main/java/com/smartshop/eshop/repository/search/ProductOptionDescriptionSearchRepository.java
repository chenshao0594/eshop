package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductOptionDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductOptionDescription entity.
 */
public interface ProductOptionDescriptionSearchRepository extends ElasticsearchRepository<ProductOptionDescription, Long> {
}
