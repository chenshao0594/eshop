package com.smartshop.core.tax.service;

import java.util.List;

import com.smartshop.core.salesorder.model.SalesOrderSummary;
import com.smartshop.core.tax.model.TaxConfiguration;
import com.smartshop.core.tax.model.TaxItem;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.exception.BusinessException;

public interface TaxService {
	/**
	 * Retrieves tax configurations (TaxConfiguration) for a given MerchantStore
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	TaxConfiguration getTaxConfiguration(MerchantStore store) throws BusinessException;

	/**
	 * Saves ShippingConfiguration to MerchantConfiguration table
	 *
	 * @param shippingConfiguration
	 * @param store
	 * @throws BusinessException
	 */
	void saveTaxConfiguration(TaxConfiguration shippingConfiguration, MerchantStore store) throws BusinessException;

	/**
	 * Calculates tax over an OrderSummary
	 *
	 * @param orderSummary
	 * @param customer
	 * @param store
	 * @param locale
	 * @return
	 * @throws BusinessException
	 */
	List<TaxItem> calculateTax(SalesOrderSummary orderSummary, Customer customer, MerchantStore store,
			Language language) throws BusinessException;

}
