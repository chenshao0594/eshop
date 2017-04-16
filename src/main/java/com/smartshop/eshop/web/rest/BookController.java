package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Book;
import com.smartshop.eshop.service.BookService;

/**
 * REST controller for managing Book.
 */
@RestController
@RequestMapping("/api/"+ BookController.SECTION_KEY)
public class BookController extends AbstractDomainController< Book, Long>{

    private final Logger log = LoggerFactory.getLogger(BookController.class);
    public static final String SECTION_KEY = "books";
    private static final String ENTITY_NAME = "book";
        
     private final BookService bookService;

    public BookController(BookService bookService) {
        super(bookService);
        this.bookService = bookService;
    }    
    @Override
    protected String getSectionKey() {
        return SECTION_KEY;
    }

    @Override
    protected String getEntityName() {
        return ENTITY_NAME;
    }

}