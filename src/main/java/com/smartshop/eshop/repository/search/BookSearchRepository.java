package com.smartshop.eshop.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.smartshop.eshop.domain.Book;

/**
 * Spring Data Elasticsearch repository for the Book entity.
 */
public interface BookSearchRepository extends ElasticsearchRepository<Book, Long> {
}
