package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductOptionValueDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductOptionValueDescription entity.
 */
public interface ProductOptionValueDescriptionSearchRepository extends ElasticsearchRepository<ProductOptionValueDescription, Long> {
}
