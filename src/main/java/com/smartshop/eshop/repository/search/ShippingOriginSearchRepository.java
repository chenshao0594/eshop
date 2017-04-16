package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ShippingOrigin;

/**
 * Spring Data Elasticsearch repository for the ShippingOrigin entity.
 */
public interface ShippingOriginSearchRepository extends ElasticsearchRepository<ShippingOrigin, Long> {
}
