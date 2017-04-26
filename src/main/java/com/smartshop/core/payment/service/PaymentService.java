package com.smartshop.core.payment.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.core.payment.modules.PaymentModule;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.payment.dto.CreditCardEnum;
import com.smartshop.payment.dto.PaymentDTO;
import com.smartshop.payment.dto.PaymentMethodDTO;

public interface PaymentService {
	public List<IntegrationModule> getPaymentMethods(MerchantStore store) throws BusinessException;

	Map<String, IntegrationConfiguration> getPaymentModulesConfigured(MerchantStore store) throws BusinessException;

	Transaction processPayment(Customer customer, MerchantStore store, PaymentDTO payment, List<ShoppingCartItem> items,
			SalesOrder order) throws BusinessException;

	Transaction processRefund(SalesOrder order, Customer customer, MerchantStore store, BigDecimal amount)
			throws BusinessException;

	/**
	 * Get a specific Payment module by payment type CREDITCART, MONEYORDER ...
	 * 
	 * @param store
	 * @param type
	 *            (payment type)
	 * @return IntegrationModule
	 * @throws BusinessException
	 */
	IntegrationModule getPaymentMethodByType(MerchantStore store, String type) throws BusinessException;

	/**
	 * Get a specific Payment module by payment code (defined in
	 * integrationmoduel.json) paypal, authorizenet ..
	 * 
	 * @param store
	 * @param name
	 * @return IntegrationModule
	 * @throws BusinessException
	 */
	IntegrationModule getPaymentMethodByCode(MerchantStore store, String name) throws BusinessException;

	/**
	 * Saves a payment module configuration
	 * 
	 * @param configuration
	 * @param store
	 * @throws BusinessException
	 */
	void savePaymentModuleConfiguration(IntegrationConfiguration configuration, MerchantStore store)
			throws BusinessException;

	/**
	 * Validates if the credit card input information are correct
	 * 
	 * @param number
	 * @param type
	 * @param month
	 * @param date
	 * @throws BusinessException
	 */
	void validateCreditCard(String number, CreditCardEnum creditCard, String month, String date)
			throws BusinessException;

	/**
	 * Get the integration configuration for a specific payment module
	 * 
	 * @param moduleCode
	 * @param store
	 * @return IntegrationConfiguration
	 * @throws BusinessException
	 */
	IntegrationConfiguration getPaymentConfiguration(String moduleCode, MerchantStore store) throws BusinessException;

	void removePaymentModuleConfiguration(String moduleCode, MerchantStore store) throws BusinessException;

	Transaction processCapturePayment(SalesOrder order, Customer customer, MerchantStore store)
			throws BusinessException;

	List<PaymentMethodDTO> getAcceptedPaymentMethods(MerchantStore store) throws BusinessException;

	/**
	 * Returns a PaymentModule based on the payment code
	 * 
	 * @param paymentModuleCode
	 * @return PaymentModule
	 * @throws BusinessException
	 */
	PaymentModule getPaymentModule(String paymentModuleCode) throws BusinessException;

}
