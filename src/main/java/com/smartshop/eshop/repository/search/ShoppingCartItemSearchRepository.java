package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ShoppingCartItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShoppingCartItem entity.
 */
public interface ShoppingCartItemSearchRepository extends ElasticsearchRepository<ShoppingCartItem, Long> {
}
