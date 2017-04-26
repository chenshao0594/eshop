package com.smartshop.core.payment.modules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.domain.enumeration.PaymentEnum;
import com.smartshop.eshop.domain.enumeration.TransactionEnum;
import com.smartshop.eshop.exception.IntegrationException;
import com.smartshop.payment.dto.PaymentDTO;

public class MoneyOrderPayment implements PaymentModule {

	@Override
	public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store)
			throws IntegrationException {

		List<String> errorFields = null;

		Map<String, String> keys = integrationConfiguration.getIntegrationKeys();

		// validate integrationKeys['address']
		if (keys == null || StringUtils.isBlank(keys.get("address"))) {
			errorFields = new ArrayList<String>();
			errorFields.add("address");
		}

		if (errorFields != null) {
			IntegrationException ex = new IntegrationException(IntegrationException.ERROR_VALIDATION_SAVE);
			ex.setErrorFields(errorFields);
			throw ex;

		}

		return;

	}

	@Override
	public Transaction initTransaction(MerchantStore store, Customer customer, BigDecimal amount, PaymentDTO payment,
			IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException {
		// NOT REQUIRED
		return null;
	}

	@Override
	public Transaction authorize(MerchantStore store, Customer customer, List<ShoppingCartItem> items,
			BigDecimal amount, PaymentDTO payment, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		// NOT REQUIRED
		return null;
	}

	/*
	 * @Override public Transaction capture(MerchantStore store, Customer
	 * customer, List<ShoppingCartItem> items, BigDecimal amount, Payment
	 * payment, Transaction transaction, IntegrationConfiguration configuration,
	 * IntegrationModule module) throws IntegrationException { //NOT REQUIRED
	 * return null; }
	 */

	@Override
	public Transaction authorizeAndCapture(MerchantStore store, Customer customer, List<ShoppingCartItem> items,
			BigDecimal amount, PaymentDTO payment, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType(TransactionEnum.AUTHORIZECAPTURE);
		transaction.setPaymentType(PaymentEnum.MONEYORDER);

		return transaction;

	}

	@Override
	public Transaction refund(boolean partial, MerchantStore store, Transaction transaction, SalesOrder order,
			BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		throw new IntegrationException("Transaction not supported");
	}

	@Override
	public Transaction capture(MerchantStore store, Customer customer, SalesOrder order,
			Transaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException {
		// TODO Auto-generated method stub
		return null;
	}

}
