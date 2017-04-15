package com.smartshop.eshop.repository.search;

import com.smartshop.eshop.domain.Transaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Transaction entity.
 */
public interface TransactionSearchRepository extends ElasticsearchRepository<Transaction, Long> {
}
