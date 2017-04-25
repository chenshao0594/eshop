package com.smartshop.core.catelog.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.smartshop.eshop.core.catalog.product.FinalPrice;
import com.smartshop.eshop.domain.Currency;
import com.smartshop.eshop.domain.Customer;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.Product;
import com.smartshop.eshop.domain.ProductAttribute;
import com.smartshop.eshop.exception.BusinessException;

/**
 * Services for Product item price calculation.
 *
 * @author Carl Samson
 *
 */
public interface PricingService {

	/**
	 * Calculates the FinalPrice of a Product taking into account all defined
	 * prices and possible rebates
	 *
	 * @param product
	 * @return
	 * @throws BusinessException
	 */
	FinalPrice calculateProductPrice(Product product) throws BusinessException;

	/**
	 * Calculates the FinalPrice of a Product taking into account all defined
	 * prices and possible rebates. It also applies other calculation based on
	 * the customer
	 *
	 * @param product
	 * @param customer
	 * @return
	 * @throws BusinessException
	 */
	FinalPrice calculateProductPrice(Product product, Customer customer) throws BusinessException;

	/**
	 * Calculates the FinalPrice of a Product taking into account all defined
	 * prices and possible rebates. This method should be used to calculate any
	 * additional prices based on the default attributes or based on the user
	 * selected attributes.
	 *
	 * @param product
	 * @param attributes
	 * @return
	 * @throws BusinessException
	 */
	FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes) throws BusinessException;

	/**
	 * Calculates the FinalPrice of a Product taking into account all defined
	 * prices and possible rebates. This method should be used to calculate any
	 * additional prices based on the default attributes or based on the user
	 * selected attributes. It also applies other calculation based on the
	 * customer
	 *
	 * @param product
	 * @param attributes
	 * @param customer
	 * @return
	 * @throws BusinessException
	 */
	FinalPrice calculateProductPrice(Product product, List<ProductAttribute> attributes, Customer customer)
			throws BusinessException;

	/**
	 * Method to be used to print a displayable formated amount to the end user
	 *
	 * @param amount
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	String getDisplayAmount(BigDecimal amount, MerchantStore store) throws BusinessException;

	/**
	 * Method to be used when building an amount formatted with the appropriate
	 * currency
	 *
	 * @param amount
	 * @param locale
	 * @param currency
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	String getDisplayAmount(BigDecimal amount, Locale locale, Currency currency, MerchantStore store)
			throws BusinessException;

	/**
	 * String format of the money amount without currency symbol
	 *
	 * @param amount
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	String getStringAmount(BigDecimal amount, MerchantStore store) throws BusinessException;
}
