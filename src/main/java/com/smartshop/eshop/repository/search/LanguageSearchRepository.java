package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Language;

/**
 * Spring Data Elasticsearch repository for the Language entity.
 */
public interface LanguageSearchRepository extends ElasticsearchRepository<Language, Long> {
}
