package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ZoneDescription;

/**
 * Spring Data Elasticsearch repository for the ZoneDescription entity.
 */
public interface ZoneDescriptionSearchRepository extends ElasticsearchRepository<ZoneDescription, Long> {
}
