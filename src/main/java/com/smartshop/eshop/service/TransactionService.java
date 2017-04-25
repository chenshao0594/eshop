package com.smartshop.eshop.service;

import java.util.List;

import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService extends AbstractDomainService<Transaction, Long> {
	/**
	 * Obtain a previous transaction that has type authorize for a give order
	 * 
	 * @param order
	 * @return
	 * @throws BusinessException
	 */
	Transaction getCapturableTransaction(SalesOrder order) throws BusinessException;

	Transaction getRefundableTransaction(SalesOrder order) throws BusinessException;

	List<Transaction> listTransactions(SalesOrder order) throws BusinessException;

}