package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ShoppingCartItem;

/**
 * Spring Data Elasticsearch repository for the ShoppingCartItem entity.
 */
public interface ShoppingCartItemSearchRepository extends ElasticsearchRepository<ShoppingCartItem, Long> {
}
