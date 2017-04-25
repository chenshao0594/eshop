package com.smartshop.core.shipping.integration.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import com.smartshop.core.integration.CustomIntegrationConfiguration;
import com.smartshop.core.model.system.IntegrationConfiguration;
import com.smartshop.core.shipping.model.PackageDetails;
import com.smartshop.core.shipping.model.ShippingConfiguration;
import com.smartshop.core.shipping.model.ShippingOption;
import com.smartshop.core.shipping.model.ShippingQuote;
import com.smartshop.eshop.domain.IntegrationModule;
import com.smartshop.eshop.domain.MerchantStore;
import com.smartshop.eshop.domain.ShippingOrigin;
import com.smartshop.eshop.domain.common.Delivery;
import com.smartshop.eshop.exception.IntegrationException;

public interface ShippingQuoteModule {

	public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store)
			throws IntegrationException;

	public CustomIntegrationConfiguration getCustomModuleConfiguration(MerchantStore store) throws IntegrationException;

	public List<ShippingOption> getShippingQuotes(ShippingQuote quote, List<PackageDetails> packages,
			BigDecimal orderTotal, Delivery delivery, ShippingOrigin origin, MerchantStore store,
			IntegrationConfiguration configuration, IntegrationModule module,
			ShippingConfiguration shippingConfiguration, Locale locale) throws IntegrationException;

}
