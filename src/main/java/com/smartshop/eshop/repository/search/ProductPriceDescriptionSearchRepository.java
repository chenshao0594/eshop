package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ProductPriceDescription;

/**
 * Spring Data Elasticsearch repository for the ProductPriceDescription entity.
 */
public interface ProductPriceDescriptionSearchRepository
		extends ElasticsearchRepository<ProductPriceDescription, Long> {
}
