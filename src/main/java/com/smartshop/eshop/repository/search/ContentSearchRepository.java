package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Content;

/**
 * Spring Data Elasticsearch repository for the Content entity.
 */
public interface ContentSearchRepository extends ElasticsearchRepository<Content, Long> {
}
