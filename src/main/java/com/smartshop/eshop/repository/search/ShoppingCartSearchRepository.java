package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ShoppingCart;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShoppingCart entity.
 */
public interface ShoppingCartSearchRepository extends ElasticsearchRepository<ShoppingCart, Long> {
}
