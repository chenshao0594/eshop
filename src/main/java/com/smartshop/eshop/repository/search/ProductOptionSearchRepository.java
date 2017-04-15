package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductOption;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductOption entity.
 */
public interface ProductOptionSearchRepository extends ElasticsearchRepository<ProductOption, Long> {
}
