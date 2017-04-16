package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.SystemNotification;

/**
 * Spring Data Elasticsearch repository for the SystemNotification entity.
 */
public interface SystemNotificationSearchRepository extends ElasticsearchRepository<SystemNotification, Long> {
}
