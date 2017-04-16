package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Template;

/**
 * Spring Data Elasticsearch repository for the Template entity.
 */
public interface TemplateSearchRepository extends ElasticsearchRepository<Template, Long> {
}
