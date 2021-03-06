package com.smartshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.smartshop.eshop.domain.Book;

/**
 * Spring Data JPA repository for the Book entity.
 */
@SuppressWarnings("unused")
public interface BookRepository extends JpaRepository<Book, Long>,QueryDslPredicateExecutor<Book> {

}
