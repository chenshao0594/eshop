package com.smartshop.eshop.service.impl;

import com.smartshop.eshop.service.TransactionService;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.repository.TransactionRepository;
import com.smartshop.eshop.repository.search.TransactionSearchRepository;
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
 * Service Implementation for managing Transaction.
 */
@Service
@Transactional
public class TransactionServiceImpl extends AbstractDomainServiceImpl< Transaction, Long> implements TransactionService{

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final TransactionRepository transactionRepository;
    private final TransactionSearchRepository transactionSearchRepository;
    
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionSearchRepository transactionSearchRepository) {
        super(transactionRepository,transactionSearchRepository);
        this.transactionRepository = transactionRepository;
        this.transactionSearchRepository = transactionSearchRepository;
    }
    
}
