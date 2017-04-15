package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.SystemNotification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SystemNotification entity.
 */
public interface SystemNotificationSearchRepository extends ElasticsearchRepository<SystemNotification, Long> {
}
