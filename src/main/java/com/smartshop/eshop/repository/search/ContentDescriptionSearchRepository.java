package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.ContentDescription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ContentDescription entity.
 */
public interface ContentDescriptionSearchRepository extends ElasticsearchRepository<ContentDescription, Long> {
}
