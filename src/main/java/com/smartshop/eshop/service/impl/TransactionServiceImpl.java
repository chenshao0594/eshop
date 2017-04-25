package com.smartshop.eshop.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.domain.enumeration.TransactionEnum;
import com.smartshop.eshop.exception.BusinessException;
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

	@Override
	public Transaction save(Transaction transaction) {

		// parse JSON string
		String transactionDetails = transaction.toJSONString();
		if (!StringUtils.isBlank(transactionDetails)) {
			transaction.setDetails(transactionDetails);
		}

		return super.save(transaction);

	}

	@Override
	public List<Transaction> listTransactions(SalesOrder order) throws BusinessException {

		List<Transaction> transactions = transactionRepository.findByOrder(order.getId());
		ObjectMapper mapper = new ObjectMapper();
		for (Transaction transaction : transactions) {
			if (!StringUtils.isBlank(transaction.getDetails())) {
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> objects = mapper.readValue(transaction.getDetails(), Map.class);
					transaction.setTransactionDetails(objects);
				} catch (Exception e) {
					throw new BusinessException(e);
				}
			}
		}

		return transactions;
	}

	@Override
	public Transaction getCapturableTransaction(SalesOrder order) throws BusinessException {
		List<Transaction> transactions = transactionRepository.findByOrder(order.getId());
		ObjectMapper mapper = new ObjectMapper();
		Transaction capturable = null;
		for (Transaction transaction : transactions) {
			if (transaction.getTransactionType().name().equals(TransactionEnum.AUTHORIZE.name())) {
				if (!StringUtils.isBlank(transaction.getDetails())) {
					try {
						@SuppressWarnings("unchecked")
						Map<String, String> objects = mapper.readValue(transaction.getDetails(), Map.class);
						transaction.setTransactionDetails(objects);
						capturable = transaction;
					} catch (Exception e) {
						throw new BusinessException(e);
					}
				}
			}
			if (transaction.getTransactionType().name().equals(TransactionEnum.CAPTURE.name())) {
				break;
			}
			if (transaction.getTransactionType().name().equals(TransactionEnum.REFUND.name())) {
				break;
			}
		}

		return capturable;
	}

	@Override
	public Transaction getRefundableTransaction(SalesOrder order) throws BusinessException {
		List<Transaction> transactions = transactionRepository.findByOrder(order.getId());
		Map<String, Transaction> finalTransactions = new HashMap<String, Transaction>();
		Transaction finalTransaction = null;
		for (Transaction transaction : transactions) {
			if (transaction.getTransactionType().name().equals(TransactionEnum.AUTHORIZECAPTURE.name())) {
				finalTransactions.put(TransactionEnum.AUTHORIZECAPTURE.name(), transaction);
				continue;
			}
			if (transaction.getTransactionType().name().equals(TransactionEnum.CAPTURE.name())) {
				finalTransactions.put(TransactionEnum.CAPTURE.name(), transaction);
				continue;
			}
			if (transaction.getTransactionType().name().equals(TransactionEnum.REFUND.name())) {
				// check transaction id
				Transaction previousRefund = finalTransactions.get(TransactionEnum.REFUND.name());
				if (previousRefund != null) {
					Date previousDate = previousRefund.getTransactionDate();
					Date currentDate = transaction.getTransactionDate();
					if (previousDate.before(currentDate)) {
						finalTransactions.put(TransactionEnum.REFUND.name(), transaction);
						continue;
					}
				} else {
					finalTransactions.put(TransactionEnum.REFUND.name(), transaction);
					continue;
				}
			}
		}

		if (finalTransactions.containsKey(TransactionEnum.AUTHORIZECAPTURE.name())) {
			finalTransaction = finalTransactions.get(TransactionEnum.AUTHORIZECAPTURE.name());
		}

		if (finalTransactions.containsKey(TransactionEnum.CAPTURE.name())) {
			finalTransaction = finalTransactions.get(TransactionEnum.CAPTURE.name());
		}

		// if(finalTransactions.containsKey(TransactionEnum.REFUND.name())) {
		// finalTransaction =
		// finalTransactions.get(TransactionEnum.REFUND.name());
		// }

		if (finalTransaction != null && !StringUtils.isBlank(finalTransaction.getDetails())) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				@SuppressWarnings("unchecked")
				Map<String, String> objects = mapper.readValue(finalTransaction.getDetails(), Map.class);
				finalTransaction.setTransactionDetails(objects);
			} catch (Exception e) {
				throw new BusinessException(e);
			}
		}

		return finalTransaction;
	}

}
