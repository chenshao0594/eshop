package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.Content;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Content entity.
 */
public interface ContentSearchRepository extends ElasticsearchRepository<Content, Long> {
}
