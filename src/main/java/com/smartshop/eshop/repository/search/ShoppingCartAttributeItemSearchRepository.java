package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;

/**
 * Spring Data Elasticsearch repository for the ShoppingCartAttributeItem
 * entity.
 */
public interface ShoppingCartAttributeItemSearchRepository
		extends ElasticsearchRepository<ShoppingCartAttributeItem, Long> {
}
