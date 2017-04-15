package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.GeoZone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GeoZone entity.
 */
public interface GeoZoneSearchRepository extends ElasticsearchRepository<GeoZone, Long> {
}
