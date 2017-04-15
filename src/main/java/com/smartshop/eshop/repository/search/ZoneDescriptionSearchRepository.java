package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ZoneDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ZoneDescription entity.
 */
public interface ZoneDescriptionSearchRepository extends ElasticsearchRepository<ZoneDescription, Long> {
}
