package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.GeoZoneDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GeoZoneDescription entity.
 */
public interface GeoZoneDescriptionSearchRepository extends ElasticsearchRepository<GeoZoneDescription, Long> {
}
