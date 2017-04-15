package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ShoppingCartAttributeItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShoppingCartAttributeItem entity.
 */
public interface ShoppingCartAttributeItemSearchRepository extends ElasticsearchRepository<ShoppingCartAttributeItem, Long> {
}
