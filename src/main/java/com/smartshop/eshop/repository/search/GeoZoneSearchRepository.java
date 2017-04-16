package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.GeoZone;

/**
 * Spring Data Elasticsearch repository for the GeoZone entity.
 */
public interface GeoZoneSearchRepository extends ElasticsearchRepository<GeoZone, Long> {
}
