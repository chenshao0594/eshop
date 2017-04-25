package com.smartshop.core.shipping.service;

import java.util.List;
import java.util.Map;

import com.smartshop.core.integration.CustomIntegrationConfiguration;
import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.core.shipping.model.PackageDetails;
import com.smartshop.core.shipping.model.ShippingConfiguration;
import com.smartshop.core.shipping.model.ShippingMetaData;
import com.smartshop.core.shipping.model.ShippingOption;
import com.smartshop.core.shipping.model.ShippingProduct;
import com.smartshop.core.shipping.model.ShippingQuote;
import com.smartshop.core.shipping.model.ShippingSummary;
import com.smartshop.eshop.domain.Country;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.Language;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.ShoppingCartItem;
import com.smartshop.eshop.domain.common.Delivery;
import com.smartshop.eshop.exception.BusinessException;

public interface ShippingService {
	/**
	 * Returns a list of supported countries (ship to country list) configured
	 * by merchant when the merchant configured shipping National and has saved
	 * a list of ship to country from the list
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	public List<String> getSupportedCountries(MerchantStore store) throws BusinessException;

	public void setSupportedCountries(MerchantStore store, List<String> countryCodes) throws BusinessException;

	/**
	 * Returns a list of available shipping modules
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	public List<IntegrationModule> getShippingMethods(MerchantStore store) throws BusinessException;

	/**
	 * Returns a list of configured shipping modules for a given merchant
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	Map<String, IntegrationConfiguration> getShippingModulesConfigured(MerchantStore store) throws BusinessException;

	/**
	 * Adds a Shipping configuration
	 *
	 * @param configuration
	 * @param store
	 * @throws BusinessException
	 */
	void saveShippingQuoteModuleConfiguration(IntegrationConfiguration configuration, MerchantStore store)
			throws BusinessException;

	/**
	 * ShippingType (NATIONAL, INTERNATIONSL) ShippingBasisType (SHIPPING,
	 * BILLING) ShippingPriceOptionType (ALL, LEAST, HIGHEST) Packages Handling
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	ShippingConfiguration getShippingConfiguration(MerchantStore store) throws BusinessException;

	/**
	 * Saves ShippingConfiguration for a given MerchantStore
	 *
	 * @param shippingConfiguration
	 * @param store
	 * @throws BusinessException
	 */
	void saveShippingConfiguration(ShippingConfiguration shippingConfiguration, MerchantStore store)
			throws BusinessException;

	void removeShippingQuoteModuleConfiguration(String moduleCode, MerchantStore store) throws BusinessException;

	/**
	 * Provides detailed information on boxes that will be used when getting a
	 * ShippingQuote
	 *
	 * @param products
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	List<PackageDetails> getPackagesDetails(List<ShippingProduct> products, MerchantStore store)
			throws BusinessException;

	/**
	 * Get a list of ShippingQuote from a configured shipping gateway. The
	 * quotes are displayed to the end user so he can pick the ShippingOption he
	 * wants
	 *
	 * @param store
	 * @param customer
	 * @param products
	 * @param language
	 * @return
	 * @throws BusinessException
	 */
	ShippingQuote getShippingQuote(MerchantStore store, Delivery delivery, List<ShippingProduct> products,
			Language language) throws BusinessException;

	/**
	 * Returns a shipping module configuration given a moduleCode
	 *
	 * @param moduleCode
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	IntegrationConfiguration getShippingConfiguration(String moduleCode, MerchantStore store) throws BusinessException;

	/**
	 * Retrieves the custom configuration for a given module
	 *
	 * @param moduleCode
	 * @param store
	 * @return
	 * @throws BusinessException
	 */

	CustomIntegrationConfiguration getCustomShippingConfiguration(String moduleCode, MerchantStore store)
			throws BusinessException;

	/**
	 * Weight based configuration
	 *
	 * @param moduleCode
	 * @param shippingConfiguration
	 * @param store
	 * @throws BusinessException
	 */
	void saveCustomShippingConfiguration(String moduleCode, CustomIntegrationConfiguration shippingConfiguration,
			MerchantStore store) throws BusinessException;

	/**
	 * Removes a custom shipping quote module
	 *
	 * @param moduleCode
	 * @param store
	 * @throws BusinessException
	 */
	void removeCustomShippingQuoteModuleConfiguration(String moduleCode, MerchantStore store) throws BusinessException;

	/**
	 * The {@link ShippingSummary} is built from the ShippingOption the user has
	 * selected The ShippingSummary is used for order calculation
	 *
	 * @param store
	 * @param shippingQuote
	 * @param selectedShippingOption
	 * @return
	 * @throws BusinessException
	 */
	ShippingSummary getShippingSummary(MerchantStore store, ShippingQuote shippingQuote,
			ShippingOption selectedShippingOption) throws BusinessException;

	/**
	 * Returns a list of supported countries (ship to country list) configured
	 * by merchant If the merchant configured shipping National, then only store
	 * country will be in the list If the merchant configured shipping
	 * International, then the list of accepted country is returned from the
	 * list
	 *
	 * @param store
	 * @param language
	 * @return
	 * @throws BusinessException
	 */
	List<Country> getShipToCountryList(MerchantStore store, Language language) throws BusinessException;

	/**
	 * Determines if Shipping should be proposed to the customer
	 *
	 * @param items
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	boolean requiresShipping(List<ShoppingCartItem> items, MerchantStore store) throws BusinessException;

	/**
	 * Returns shipping metadata and how shipping is configured for a given
	 * store
	 *
	 * @param store
	 * @return
	 * @throws BusinessException
	 */
	ShippingMetaData getShippingMetaData(MerchantStore store) throws BusinessException;

}
