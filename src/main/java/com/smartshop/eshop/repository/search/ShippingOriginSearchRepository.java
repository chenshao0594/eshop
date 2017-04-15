package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ShippingOrigin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShippingOrigin entity.
 */
public interface ShippingOriginSearchRepository extends ElasticsearchRepository<ShippingOrigin, Long> {
}
