package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.FileHistory;

/**
 * Spring Data Elasticsearch repository for the FileHistory entity.
 */
public interface FileHistorySearchRepository extends ElasticsearchRepository<FileHistory, Long> {
}
