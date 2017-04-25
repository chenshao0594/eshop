package com.smartshop.core.payment.modules;

import java.math.BigDecimal;
import java.util.List;

import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.exception.IntegrationException;
import com.smartshop.payment.dto.PaymentDTO;

public interface PaymentModule {

	public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store)
			throws IntegrationException;

	/**
	 * Returns token-value related to the initialization of the transaction This
	 * method is invoked for paypal express checkout
	 * 
	 * @param customer
	 * @param order
	 * @return
	 * @throws IntegrationException
	 */
	public Transaction initTransaction(MerchantStore store, Customer customer, BigDecimal amount, PaymentDTO payment,
			IntegrationConfiguration configuration, IntegrationModule module) throws IntegrationException;

	public Transaction authorize(MerchantStore store, Customer customer, List<ShoppingCartItem> items,
			BigDecimal amount, PaymentDTO payment, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException;

	public Transaction capture(MerchantStore store, Customer customer, SalesOrder order,
			Transaction capturableTransaction, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException;

	public Transaction authorizeAndCapture(MerchantStore store, Customer customer, List<ShoppingCartItem> items,
			BigDecimal amount, PaymentDTO payment, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException;

	public Transaction refund(boolean partial, MerchantStore store, Transaction transaction, SalesOrder order,
			BigDecimal amount, IntegrationConfiguration configuration, IntegrationModule module)
			throws IntegrationException;

}
