package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.EmailTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmailTemplate entity.
 */
public interface EmailTemplateSearchRepository extends ElasticsearchRepository<EmailTemplate, Long> {
}
