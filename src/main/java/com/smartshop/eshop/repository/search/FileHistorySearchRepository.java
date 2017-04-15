package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.FileHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the FileHistory entity.
 */
public interface FileHistorySearchRepository extends ElasticsearchRepository<FileHistory, Long> {
}
