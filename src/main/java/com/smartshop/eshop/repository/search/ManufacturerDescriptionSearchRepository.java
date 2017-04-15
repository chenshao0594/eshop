package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ManufacturerDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ManufacturerDescription entity.
 */
public interface ManufacturerDescriptionSearchRepository extends ElasticsearchRepository<ManufacturerDescription, Long> {
}
