package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.GeoZoneDescription;

/**
 * Spring Data Elasticsearch repository for the GeoZoneDescription entity.
 */
public interface GeoZoneDescriptionSearchRepository extends ElasticsearchRepository<GeoZoneDescription, Long> {
}
