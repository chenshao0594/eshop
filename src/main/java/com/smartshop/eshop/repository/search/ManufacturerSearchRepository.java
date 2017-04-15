package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.Manufacturer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Manufacturer entity.
 */
public interface ManufacturerSearchRepository extends ElasticsearchRepository<Manufacturer, Long> {
}
