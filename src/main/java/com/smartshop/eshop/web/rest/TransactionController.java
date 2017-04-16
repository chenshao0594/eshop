package com.smartshop.eshop.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.service.TransactionService;

/**
 * REST controller for managing Transaction.
 */
@RestController
@RequestMapping("/api/" + TransactionController.SECTION_KEY)
public class TransactionController extends AbstractDomainController<Transaction, Long> {

	private final Logger log = LoggerFactory.getLogger(TransactionController.class);
	public static final String SECTION_KEY = "transactions";
	private static final String ENTITY_NAME = "transaction";

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		super(transactionService);
		this.transactionService = transactionService;
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