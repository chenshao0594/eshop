package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.CountryDescription;

/**
 * Spring Data Elasticsearch repository for the CountryDescription entity.
 */
public interface CountryDescriptionSearchRepository extends ElasticsearchRepository<CountryDescription, Long> {
}
