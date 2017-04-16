package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ShoppingCart;

/**
 * Spring Data Elasticsearch repository for the ShoppingCart entity.
 */
public interface ShoppingCartSearchRepository extends ElasticsearchRepository<ShoppingCart, Long> {
}
