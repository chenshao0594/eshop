package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.DigitalProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DigitalProduct entity.
 */
public interface DigitalProductSearchRepository extends ElasticsearchRepository<DigitalProduct, Long> {
}
