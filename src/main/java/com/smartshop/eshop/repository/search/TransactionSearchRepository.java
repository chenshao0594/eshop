package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Transaction;

/**
 * Spring Data Elasticsearch repository for the Transaction entity.
 */
public interface TransactionSearchRepository extends ElasticsearchRepository<Transaction, Long> {
}
