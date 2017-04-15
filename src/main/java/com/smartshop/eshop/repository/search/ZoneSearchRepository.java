package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.Zone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Zone entity.
 */
public interface ZoneSearchRepository extends ElasticsearchRepository<Zone, Long> {
}
