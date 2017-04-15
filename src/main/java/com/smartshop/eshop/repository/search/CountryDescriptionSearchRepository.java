package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.CountryDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CountryDescription entity.
 */
public interface CountryDescriptionSearchRepository extends ElasticsearchRepository<CountryDescription, Long> {
}
