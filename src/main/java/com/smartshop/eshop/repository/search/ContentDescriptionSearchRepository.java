package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ContentDescription;

/**
 * Spring Data Elasticsearch repository for the ContentDescription entity.
 */
public interface ContentDescriptionSearchRepository extends ElasticsearchRepository<ContentDescription, Long> {
}
