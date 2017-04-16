package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Manufacturer;

/**
 * Spring Data Elasticsearch repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends ElasticsearchRepository<Manufacturer, Long> {
}
