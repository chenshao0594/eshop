package com.smartshop.eshop.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.repository.TransactionRepository;
import com.smartshop.eshop.repository.search.TransactionSearchRepository;
import com.smartshop.eshop.service.TransactionService;

/**
 * Service Implementation for managing Transaction.
 */
@Service
@Transactional
public class TransactionServiceImpl extends AbstractDomainServiceImpl<Transaction, Long> implements TransactionService {

	private final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
	private final TransactionRepository transactionRepository;
	private final TransactionSearchRepository transactionSearchRepository;

	public TransactionServiceImpl(TransactionRepository transactionRepository,
			TransactionSearchRepository transactionSearchRepository) {
		super(transactionRepository, transactionSearchRepository);
		this.transactionRepository = transactionRepository;
		this.transactionSearchRepository = transactionSearchRepository;
	}

}
