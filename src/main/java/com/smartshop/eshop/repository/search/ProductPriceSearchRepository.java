package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductPrice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductPrice entity.
 */
public interface ProductPriceSearchRepository extends ElasticsearchRepository<ProductPrice, Long> {
}
