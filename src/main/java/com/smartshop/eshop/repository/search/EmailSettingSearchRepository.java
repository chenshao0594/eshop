package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.EmailSetting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EmailSetting entity.
 */
public interface EmailSettingSearchRepository extends ElasticsearchRepository<EmailSetting, Long> {
}
