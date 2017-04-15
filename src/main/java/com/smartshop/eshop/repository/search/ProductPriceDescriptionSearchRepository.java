package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ProductPriceDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ProductPriceDescription entity.
 */
public interface ProductPriceDescriptionSearchRepository extends ElasticsearchRepository<ProductPriceDescription, Long> {
}
