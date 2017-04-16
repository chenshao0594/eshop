package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Zone;

/**
 * Spring Data Elasticsearch repository for the Zone entity.
 */
public interface ZoneSearchRepository extends ElasticsearchRepository<Zone, Long> {
}
