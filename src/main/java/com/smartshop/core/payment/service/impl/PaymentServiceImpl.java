package com.smartshop.core.payment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.core.payment.modules.PaymentModule;
import com.smartshop.core.payment.service.PaymentService;
import com.smartshop.core.utils.Encryption;
import com.smartshop.eshop.common.BusinessConstants;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantConfiguration;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.OrderStatusHistory;
import com.smartshop.eshop.domain.OrderTotal;
import com.smartshop.eshop.domain.SalesOrder;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.Transaction;
import com.smartshop.eshop.domain.enumeration.OrderStatusEnum;
import com.smartshop.eshop.domain.enumeration.OrderTotalEnum;
import com.smartshop.eshop.domain.enumeration.PaymentEnum;
import com.smartshop.eshop.domain.enumeration.TransactionEnum;
import com.smartshop.eshop.exception.BusinessException;
import com.smartshop.eshop.exception.IntegrationException;
import com.smartshop.eshop.service.IntegrationModuleService;
import com.smartshop.eshop.service.MerchantConfigurationService;
import com.smartshop.eshop.service.SalesOrderService;
import com.smartshop.eshop.service.TransactionService;
import com.smartshop.payment.dto.CreditCardEnum;
import com.smartshop.payment.dto.CreditCardPaymentDTO;
import com.smartshop.payment.dto.PaymentDTO;
import com.smartshop.payment.dto.PaymentMethodDTO;
import com.smartshop.reference.loader.ConfigurationModulesLoader;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	private final static String PAYMENT_MODULES = "PAYMENT";

	@Inject
	private MerchantConfigurationService merchantConfigurationService;

	@Inject
	private IntegrationModuleService moduleConfigurationService;

	@Inject
	private TransactionService transactionService;

	@Inject
	private SalesOrderService orderService;

	@Inject
	@Resource(name = "paymentModules")
	private Map<String, PaymentModule> paymentModules;

	private Encryption encryption = new Encryption();

	@Override
	public List<IntegrationModule> getPaymentMethods(MerchantStore store) throws BusinessException {

		List<IntegrationModule> modules = moduleConfigurationService.getIntegrationModules(PAYMENT_MODULES);
		List<IntegrationModule> returnModules = new ArrayList<IntegrationModule>();

		for (IntegrationModule module : modules) {
			if (module.getRegionsSet().contains(store.getCountry().getIsoCode())
					|| module.getRegionsSet().contains("*")) {

				returnModules.add(module);
			}
		}

		return returnModules;
	}

	@Override
	public List<PaymentMethodDTO> getAcceptedPaymentMethods(MerchantStore store) throws BusinessException {

		Map<String, IntegrationConfiguration> modules = this.getPaymentModulesConfigured(store);

		List<PaymentMethodDTO> returnModules = new ArrayList<PaymentMethodDTO>();

		for (String module : modules.keySet()) {
			IntegrationConfiguration config = modules.get(module);
			if (config.isActive()) {

				IntegrationModule md = this.getPaymentMethodByCode(store, config.getModuleCode());
				if (md == null) {
					continue;
				}
				PaymentMethodDTO paymentMethod = new PaymentMethodDTO();

				paymentMethod.setDefaultSelected(config.isDefaultSelected());
				paymentMethod.setPaymentMethodCode(config.getModuleCode());
				paymentMethod.setModule(md);
				paymentMethod.setInformations(config);

				PaymentEnum type = PaymentEnum.fromString(md.getType());

				/**
				 * if(md.getType().equalsIgnoreCase(PaymentType.CREDITCARD.name()))
				 * { type = PaymentType.CREDITCARD; } else
				 * if(md.getType().equalsIgnoreCase(PaymentType.FREE.name())) {
				 * type = PaymentType.FREE; } else
				 * if(md.getType().equalsIgnoreCase(PaymentType.MONEYORDER.name()))
				 * { type = PaymentType.MONEYORDER; } else
				 * if(md.getType().equalsIgnoreCase(PaymentType.PAYPAL.name()))
				 * { type = PaymentType.PAYPAL; } else
				 * if(md.getType().equalsIgnoreCase(PaymentType.STRIPE.name()))
				 * { type = PaymentType.STRIPE; }
				 **/
				paymentMethod.setPaymentType(type);
				returnModules.add(paymentMethod);
			}
		}

		return returnModules;

	}

	@Override
	public IntegrationModule getPaymentMethodByType(MerchantStore store, String type) throws BusinessException {
		List<IntegrationModule> modules = getPaymentMethods(store);

		for (IntegrationModule module : modules) {
			if (module.getModule().equals(type)) {

				return module;
			}
		}

		return null;
	}

	@Override
	public IntegrationModule getPaymentMethodByCode(MerchantStore store, String code) throws BusinessException {
		List<IntegrationModule> modules = getPaymentMethods(store);

		for (IntegrationModule module : modules) {
			if (module.getCode().equals(code)) {

				return module;
			}
		}

		return null;
	}

	@Override
	public IntegrationConfiguration getPaymentConfiguration(String moduleCode, MerchantStore store)
			throws BusinessException {

		Map<String, IntegrationConfiguration> configuredModules = getPaymentModulesConfigured(store);
		if (configuredModules != null) {
			for (String key : configuredModules.keySet()) {
				if (key.equals(moduleCode)) {
					return configuredModules.get(key);
				}
			}
		}

		return null;

	}

	@Override
	public Map<String, IntegrationConfiguration> getPaymentModulesConfigured(MerchantStore store)
			throws BusinessException {
		try {
			Map<String, IntegrationConfiguration> modules = new HashMap<String, IntegrationConfiguration>();
			MerchantConfiguration merchantConfiguration = merchantConfigurationService
					.getMerchantConfiguration(PAYMENT_MODULES, store);
			if (merchantConfiguration != null) {

				if (!StringUtils.isBlank(merchantConfiguration.getValue())) {
					String decrypted = encryption.decrypt(merchantConfiguration.getValue());
					modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);
				}
			}
			return modules;

		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void savePaymentModuleConfiguration(IntegrationConfiguration configuration, MerchantStore store)
			throws BusinessException {
		// validate entries
		try {
			String moduleCode = configuration.getModuleCode();
			PaymentModule module = paymentModules.get(moduleCode);
			if (module == null) {
				throw new BusinessException("Payment module " + moduleCode + " does not exist");
			}
			module.validateModuleConfiguration(configuration, store);

		} catch (IntegrationException ie) {
			throw ie;
		}
		try {
			Map<String, IntegrationConfiguration> modules = new HashMap<String, IntegrationConfiguration>();
			MerchantConfiguration merchantConfiguration = merchantConfigurationService
					.getMerchantConfiguration(PAYMENT_MODULES, store);
			if (merchantConfiguration != null) {
				if (!StringUtils.isBlank(merchantConfiguration.getValue())) {

					String decrypted = encryption.decrypt(merchantConfiguration.getValue());

					modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);
				}
			} else {
				merchantConfiguration = new MerchantConfiguration();
				merchantConfiguration.setMerchantStore(store);
				merchantConfiguration.setKey(PAYMENT_MODULES);
			}
			modules.put(configuration.getModuleCode(), configuration);

			String configs = ConfigurationModulesLoader.toJSONString(modules);

			String encrypted = encryption.encrypt(configs);
			merchantConfiguration.setValue(encrypted);
			merchantConfigurationService.saveOrUpdate(merchantConfiguration);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void removePaymentModuleConfiguration(String moduleCode, MerchantStore store) throws BusinessException {
		try {
			Map<String, IntegrationConfiguration> modules = new HashMap<String, IntegrationConfiguration>();
			MerchantConfiguration merchantConfiguration = merchantConfigurationService
					.getMerchantConfiguration(PAYMENT_MODULES, store);
			if (merchantConfiguration != null) {
				if (!StringUtils.isBlank(merchantConfiguration.getValue())) {
					String decrypted = encryption.decrypt(merchantConfiguration.getValue());
					modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);
				}
				modules.remove(moduleCode);
				String configs = ConfigurationModulesLoader.toJSONString(modules);
				String encrypted = encryption.encrypt(configs);
				merchantConfiguration.setValue(encrypted);
				merchantConfigurationService.saveOrUpdate(merchantConfiguration);

			}

			MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(moduleCode,
					store);

			if (configuration != null) {// custom module

				merchantConfigurationService.delete(configuration);
			}

		} catch (Exception e) {
			throw new BusinessException(e);
		}

	}

	@Override
	public Transaction processPayment(Customer customer, MerchantStore store, PaymentDTO payment,
			List<ShoppingCartItem> items, SalesOrder order) throws BusinessException {

		Validate.notNull(customer);
		Validate.notNull(store);
		Validate.notNull(payment);
		Validate.notNull(order);
		Validate.notNull(order.getTotal());

		payment.setCurrency(store.getCurrency());

		BigDecimal amount = order.getTotal();

		// must have a shipping module configured
		Map<String, IntegrationConfiguration> modules = this.getPaymentModulesConfigured(store);
		if (modules == null) {
			throw new BusinessException("No payment module configured");
		}

		IntegrationConfiguration configuration = modules.get(payment.getModuleName());

		if (configuration == null) {
			throw new BusinessException("Payment module " + payment.getModuleName() + " is not configured");
		}

		if (!configuration.isActive()) {
			throw new BusinessException("Payment module " + payment.getModuleName() + " is not active");
		}

		String sTransactionType = configuration.getIntegrationKeys().get("transaction");
		if (sTransactionType == null) {
			sTransactionType = TransactionEnum.AUTHORIZECAPTURE.name();
		}

		if (sTransactionType.equals(TransactionEnum.AUTHORIZE.name())) {
			payment.setTransactionType(TransactionEnum.AUTHORIZE);
		} else {
			payment.setTransactionType(TransactionEnum.AUTHORIZECAPTURE);
		}

		PaymentModule module = this.paymentModules.get(payment.getModuleName());

		if (module == null) {
			throw new BusinessException("Payment module " + payment.getModuleName() + " does not exist");
		}

		if (payment instanceof CreditCardPaymentDTO) {
			CreditCardPaymentDTO creditCardPayment = (CreditCardPaymentDTO) payment;
			validateCreditCard(creditCardPayment.getCreditCardNumber(), creditCardPayment.getCreditCard(),
					creditCardPayment.getExpirationMonth(), creditCardPayment.getExpirationYear());
		}

		IntegrationModule integrationModule = getPaymentMethodByCode(store, payment.getModuleName());
		TransactionEnum transactionType = TransactionEnum.valueOf(sTransactionType);
		if (transactionType == null) {
			transactionType = payment.getTransactionType();
			if (transactionType.equals(TransactionEnum.CAPTURE.name())) {
				throw new BusinessException(
						"This method does not allow to process capture transaction. Use processCapturePayment");
			}
		}

		Transaction transaction = null;
		if (transactionType == TransactionEnum.AUTHORIZE) {
			transaction = module.authorize(store, customer, items, amount, payment, configuration, integrationModule);
		} else if (transactionType == TransactionEnum.AUTHORIZECAPTURE) {
			transaction = module.authorizeAndCapture(store, customer, items, amount, payment, configuration,
					integrationModule);
		} else if (transactionType == TransactionEnum.INIT) {
			transaction = module.initTransaction(store, customer, amount, payment, configuration, integrationModule);
		}

		if (transactionType != TransactionEnum.INIT) {
			transactionService.save(transaction);
		}

		if (transactionType == TransactionEnum.AUTHORIZECAPTURE) {
			order.setStatus(OrderStatusEnum.ORDERED);
			if (payment.getPaymentType().name() != PaymentEnum.MONEYORDER.name()) {
				order.setStatus(OrderStatusEnum.PROCESSED);
			}
		}

		return transaction;

	}

	@Override
	public PaymentModule getPaymentModule(String paymentModuleCode) throws BusinessException {
		return paymentModules.get(paymentModuleCode);
	}

	@Override
	public Transaction processCapturePayment(SalesOrder order, Customer customer, MerchantStore store)
			throws BusinessException {

		Validate.notNull(customer);
		Validate.notNull(store);
		Validate.notNull(order);

		// must have a shipping module configured
		Map<String, IntegrationConfiguration> modules = this.getPaymentModulesConfigured(store);
		if (modules == null) {
			throw new BusinessException("No payment module configured");
		}

		IntegrationConfiguration configuration = modules.get(order.getPaymentModuleCode());

		if (configuration == null) {
			throw new BusinessException("Payment module " + order.getPaymentModuleCode() + " is not configured");
		}

		if (!configuration.isActive()) {
			throw new BusinessException("Payment module " + order.getPaymentModuleCode() + " is not active");
		}

		PaymentModule module = this.paymentModules.get(order.getPaymentModuleCode());

		if (module == null) {
			throw new BusinessException("Payment module " + order.getPaymentModuleCode() + " does not exist");
		}

		IntegrationModule integrationModule = getPaymentMethodByCode(store, order.getPaymentModuleCode());

		// TransactionEnum transactionType = payment.getTransactionType();

		// get the previous transaction
		Transaction trx = transactionService.getCapturableTransaction(order);
		if (trx == null) {
			throw new BusinessException("No capturable transaction for order id " + order.getId());
		}
		Transaction transaction = module.capture(store, customer, order, trx, configuration, integrationModule);
		transaction.setOrder(order);

		transactionService.save(transaction);

		OrderStatusHistory orderHistory = new OrderStatusHistory();
		orderHistory.setOrder(order);
		orderHistory.setStatus(OrderStatusEnum.PROCESSED);
		orderHistory.setDateAdded(new Date());

		orderService.addOrderStatusHistory(order, orderHistory);

		order.setStatus(OrderStatusEnum.PROCESSED);
		orderService.saveOrUpdate(order);

		return transaction;

	}

	@Override
	public Transaction processRefund(SalesOrder order, Customer customer, MerchantStore store, BigDecimal amount)
			throws BusinessException {

		Validate.notNull(customer);
		Validate.notNull(store);
		Validate.notNull(amount);
		Validate.notNull(order);
		Validate.notNull(order.getOrderTotals());

		BigDecimal orderTotal = order.getTotal();

		if (amount.doubleValue() > orderTotal.doubleValue()) {
			throw new BusinessException("Invalid amount, the refunded amount is greater than the total allowed");
		}

		String module = order.getPaymentModuleCode();
		Map<String, IntegrationConfiguration> modules = this.getPaymentModulesConfigured(store);
		if (modules == null) {
			throw new BusinessException("No payment module configured");
		}

		IntegrationConfiguration configuration = modules.get(module);

		if (configuration == null) {
			throw new BusinessException("Payment module " + module + " is not configured");
		}

		PaymentModule paymentModule = this.paymentModules.get(module);

		if (paymentModule == null) {
			throw new BusinessException("Payment module " + paymentModule + " does not exist");
		}

		boolean partial = false;
		if (amount.doubleValue() != order.getTotal().doubleValue()) {
			partial = true;
		}

		IntegrationModule integrationModule = getPaymentMethodByCode(store, module);

		// get the associated transaction
		Transaction refundable = transactionService.getRefundableTransaction(order);

		if (refundable == null) {
			throw new BusinessException("No refundable transaction for this order");
		}

		Transaction transaction = paymentModule.refund(partial, store, refundable, order, amount, configuration,
				integrationModule);
		transaction.setOrder(order);
		transactionService.save(transaction);

		OrderTotal refund = new OrderTotal();
		refund.setModule(BusinessConstants.OT_REFUND_MODULE_CODE);
		refund.setText(BusinessConstants.OT_REFUND_MODULE_CODE);
		refund.setTitle(BusinessConstants.OT_REFUND_MODULE_CODE);
		refund.setOrderTotalCode(BusinessConstants.OT_REFUND_MODULE_CODE);
		refund.setOrderTotalType(OrderTotalEnum.REFUND);
		refund.setValue(amount);
		refund.setSortOrder(100);
		refund.setOrder(order);

		order.getOrderTotals().add(refund);

		// update order total
		orderTotal = orderTotal.subtract(amount);

		// update ordertotal refund
		Set<OrderTotal> totals = order.getOrderTotals();
		for (OrderTotal total : totals) {
			if (total.getModule().equals(BusinessConstants.OT_TOTAL_MODULE_CODE)) {
				total.setValue(orderTotal);
			}
		}

		order.setTotal(orderTotal);
		order.setStatus(OrderStatusEnum.REFUNDED);

		OrderStatusHistory orderHistory = new OrderStatusHistory();
		orderHistory.setOrder(order);
		orderHistory.setStatus(OrderStatusEnum.REFUNDED);
		orderHistory.setDateAdded(new Date());
		order.getOrderHistories().add(orderHistory);
		orderService.save(order);
		return transaction;
	}

	@Override
	public void validateCreditCard(String number, CreditCardEnum creditCard, String month, String date)
			throws BusinessException {

		try {
			Integer.parseInt(month);
			Integer.parseInt(date);
		} catch (NumberFormatException nfe) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid date format",
					"messages.error.creditcard.dateformat");
			throw ex;
		}

		if (StringUtils.isBlank(number)) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid card number",
					"messages.error.creditcard.number");
			throw ex;
		}

		Matcher m = Pattern.compile("[^\\d\\s.-]").matcher(number);

		if (m.find()) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid card number",
					"messages.error.creditcard.number");
			throw ex;
		}

		Matcher matcher = Pattern.compile("[\\s.-]").matcher(number);

		number = matcher.replaceAll("");
		validateCreditCardDate(Integer.parseInt(month), Integer.parseInt(date));
		validateCreditCardNumber(number, creditCard);
	}

	private void validateCreditCardDate(int m, int y) throws BusinessException {
		java.util.Calendar cal = new java.util.GregorianCalendar();
		int monthNow = cal.get(java.util.Calendar.MONTH) + 1;
		int yearNow = cal.get(java.util.Calendar.YEAR);
		if (yearNow > y) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid date format",
					"messages.error.creditcard.dateformat");
			throw ex;
		}
		// OK, change implementation
		if (yearNow == y && monthNow > m) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid date format",
					"messages.error.creditcard.dateformat");
			throw ex;
		}

	}

	@Deprecated
	/**
	 * Use commons validator CreditCardValidator
	 *
	 * @param number
	 * @param creditCard
	 * @throws ServiceException
	 */
	private void validateCreditCardNumber(String number, CreditCardEnum creditCard) throws BusinessException {

		// TODO implement
		if (CreditCardEnum.MASTERCARD.equals(creditCard.name())) {
			if (number.length() != 16 || Integer.parseInt(number.substring(0, 2)) < 51
					|| Integer.parseInt(number.substring(0, 2)) > 55) {
				BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION,
						"Invalid card number", "messages.error.creditcard.number");
				throw ex;
			}
		}

		if (CreditCardEnum.VISA.equals(creditCard.name())) {
			if ((number.length() != 13 && number.length() != 16) || Integer.parseInt(number.substring(0, 1)) != 4) {
				BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION,
						"Invalid card number", "messages.error.creditcard.number");
				throw ex;
			}
		}

		if (CreditCardEnum.AMEX.equals(creditCard.name())) {
			if (number.length() != 15 || (Integer.parseInt(number.substring(0, 2)) != 34
					&& Integer.parseInt(number.substring(0, 2)) != 37)) {
				BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION,
						"Invalid card number", "messages.error.creditcard.number");
				throw ex;
			}
		}

		if (CreditCardEnum.DINERS.equals(creditCard.name())) {
			if (number.length() != 14 || ((Integer.parseInt(number.substring(0, 2)) != 36
					&& Integer.parseInt(number.substring(0, 2)) != 38) && Integer.parseInt(number.substring(0, 3)) < 300
					|| Integer.parseInt(number.substring(0, 3)) > 305)) {
				BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION,
						"Invalid card number", "messages.error.creditcard.number");
				throw ex;
			}
		}

		if (CreditCardEnum.DISCOVERY.equals(creditCard.name())) {
			if (number.length() != 16 || Integer.parseInt(number.substring(0, 5)) != 6011) {
				BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION,
						"Invalid card number", "messages.error.creditcard.number");
				throw ex;
			}
		}

		luhnValidate(number);
	}

	// The Luhn algorithm is basically a CRC type
	// system for checking the validity of an entry.
	// All major credit cards use numbers that will
	// pass the Luhn check. Also, all of them are based
	// on MOD 10.
	@Deprecated
	private void luhnValidate(String numberString) throws BusinessException {
		char[] charArray = numberString.toCharArray();
		int[] number = new int[charArray.length];
		int total = 0;

		for (int i = 0; i < charArray.length; i++) {
			number[i] = Character.getNumericValue(charArray[i]);
		}

		for (int i = number.length - 2; i > -1; i -= 2) {
			number[i] *= 2;

			if (number[i] > 9)
				number[i] -= 9;
		}

		for (int i = 0; i < number.length; i++)
			total += number[i];

		if (total % 10 != 0) {
			BusinessException ex = new BusinessException(BusinessException.EXCEPTION_VALIDATION, "Invalid card number",
					"messages.error.creditcard.number");
			throw ex;
		}

	}

}
