package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.ManufacturerDescription;

/**
 * Spring Data Elasticsearch repository for the ManufacturerDescription entity.
 */
public interface ManufacturerDescriptionSearchRepository
		extends ElasticsearchRepository<ManufacturerDescription, Long> {
}
