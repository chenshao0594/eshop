package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.BookService;
import com.smartshop.eshop.domain.Book;
import com.smartshop.eshop.repository.BookRepository;
import com.smartshop.eshop.repository.search.BookSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Book.
 */
@Service
@Transactional
public class BookServiceImpl extends AbstractDomainServiceImpl< Book, Long> implements BookService{

    private final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final BookSearchRepository bookSearchRepository;
    
    public BookServiceImpl(BookRepository bookRepository, BookSearchRepository bookSearchRepository) {
        super(bookRepository,bookSearchRepository);
        this.bookRepository = bookRepository;
        this.bookSearchRepository = bookSearchRepository;
    }
    
}
